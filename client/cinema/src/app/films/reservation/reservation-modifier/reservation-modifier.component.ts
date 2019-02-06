import {Component, EventEmitter, Inject, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {RequetesHTTPService} from '../../requetes-http.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {AuthentificationService} from '../../../authentification/authentification.service';

@Component({
  selector: 'app-reservation-modifier',
  templateUrl: './reservation-modifier.component.html',
  styleUrls: ['./reservation-modifier.component.scss']
})
export class ReservationModifierComponent implements OnInit, OnChanges {

  @Input() eltReservation:any;
  @Output() reloadReservation = new EventEmitter();

  public listeSeance;
  public modeEdition = false;
  constructor(private httpService: RequetesHTTPService,
              public dialog: MatDialog) { }

  ngOnInit() {
  }

  ngOnChanges(change?: SimpleChanges) {
    //console.log(change.idReservation.currentValue);
  }

  onModifyReservation(elt){
    console.log(this.eltReservation.film.key);
    if(this.eltReservation !== ""){
      console.log("this.eltReservation.id:"+this.eltReservation.id);
      this.modeEdition = true;
      // en supposant que elt contient l'id film
      this.getSeances(this.eltReservation.film.key);
    }
  }

  // Recuperation des seances appelées lors du clic sur un film : pas besoin d'etre connecté
  getSeances(idFilm) {
    this.listeSeance = [];
    this.httpService.getSeance(idFilm).subscribe(
      (data) => {
        if (data !== null) {

          this.listeSeance = data;

          const dialogRef = this.dialog.open(DialogModifySeance,{
            //width: '1300px',
            height: '600px',
            data : this.listeSeance
          });

          dialogRef.afterClosed().subscribe(result => {
            if(result===true){
              console.log("listeseance+"+this.listeSeance);
              this.reloadReservation.emit()
            }else{
              // console.log("erreur");
            }
          });
        }else {
          console.log("Aucune seance");
        }
      }
    );
  }
}



@Component({
  selector: 'dialog-modif-seance',
  templateUrl: 'dialog-modif-seance.html',
})
export class DialogModifySeance implements OnInit, OnChanges {

  listeSeances:any;

  eltReservation : any = {};
  @Output() modifyReservation = new EventEmitter();

  constructor( public dialogRef: MatDialogRef<DialogModifySeance>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               private httpService : RequetesHTTPService,
               private authService: AuthentificationService) {
  }

  ngOnInit() {
    this.listeSeances = this.data;
  }

  ngOnChanges(){}

  onClickReserver(eltSeance) {
    if (eltSeance !== null) {
      console.log(this.authService.getUtilisateur());
      console.log("Ancienne reservation : "+this.eltReservation);
      console.log("Nouvelle reservation : "+eltSeance.id);
      this.httpService.modifierReservation(this.authService.getUtilisateur(), eltSeance.film.key, eltSeance.id).subscribe(
        (response) => {
          // si vrai      ATTENTION : A regarder
          if(response === null){
            this.dialogRef.close(true);
          }else {
            // si faux    ATTENTION : A regarder
            this.dialogRef.close(false);}
        }
      )
    }
  }
}
