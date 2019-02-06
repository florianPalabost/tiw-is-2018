import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RequetesHTTPService} from "../requetes-http.service";

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {

  private email = "test@test.com";
  listeReservation = null;
  listeFilm = [];

  @Input() listeReservations:any;
  @Output() reloadReser = new EventEmitter();
  @Output() deleteReser = new EventEmitter();

  constructor(private httpService : RequetesHTTPService ) { }

  ngOnInit() {
    this.httpService.getReservation(this.email).subscribe(
      (data) => {
        if (data !== null) {
          this.listeReservation = data;
          for (let res of this.listeReservation){
            this.gSN(res.seanceId);
          }
        } else {
          console.log("Aucune reservation");
        }
      }
    );
  }

  gSN(idSeance) {
    this.httpService.getFilmNom(idSeance).subscribe(
      (data) => {
        if (data !== null) {
          this.listeFilm.push(data);
        }else {
          console.log("Aucun Film");
        }
      }
    );
  }

}
