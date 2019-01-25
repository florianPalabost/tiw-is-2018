import {Component, Inject, OnInit, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatSnackBar} from "@angular/material";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {HttpParams} from "@angular/common/http";
import {AuthentificationService} from "./authentification.service";

@Component({
  selector: 'app-authentification',
  templateUrl: './authentification.component.html',
  styleUrls: ['./authentification.component.scss']
})
export class AuthentificationComponent implements OnInit {

  constructor(public dialog: MatDialog, public snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  seConnecter(){
    const dialogRef = this.dialog.open(DialogLogin, {width: '250px'});

    dialogRef.afterClosed().subscribe(result => {
      //console.log(result);
    });
  }

  sInscrire() {
    const dialogRef = this.dialog.open(DialogInscription, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      // A modifier
      /*if(result === true){
        this.snackBar.open("Utilisateur ajouté avec succès !")
      } else {
        this.snackBar.open("Erreur durant la création utilisater !")
      }*/
      this.snackBar.open("Utilisateur ajouté avec succès !")
    });
  }
}



@Component({
  selector: 'dialog-login',
  templateUrl: 'dialog-login.html',
})
export class DialogLogin implements OnInit{

  loginForm: FormGroup;
  invalidLogin: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, public dialogRef: MatDialogRef<DialogLogin>,
              @Inject(MAT_DIALOG_DATA) public data: any, private autService : AuthentificationService) { }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    const body = new HttpParams()
      .set('username', this.loginForm.controls.username.value)
      .set('password', this.loginForm.controls.password.value)
      .set('grant_type', 'password');

    /*this.autService.login(body.toString()).subscribe(data => {
      window.sessionStorage.setItem('token', JSON.stringify(data));
      console.log(window.sessionStorage.getItem('token'));
      this.router.navigate(['']);
    }, error => {
      console.log("Erreur !!!");
      alert(error.error.error_description)
    });*/
    window.sessionStorage.setItem('token', 'faketoken');
    this.autService.connectionSubscription.next()
    this.dialogRef.close()
  }

  ngOnInit() {
    window.sessionStorage.removeItem('token');
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}



@Component({
  selector: 'dialog-inscription',
  templateUrl: 'dialog-inscription.html',
})
export class DialogInscription implements OnInit{

  addForm: FormGroup;
  loginForm: FormGroup;
  invalidLogin: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, public dialogRef: MatDialogRef<DialogInscription>,
              @Inject(MAT_DIALOG_DATA) public data: any, private autService : AuthentificationService) { }

  onSubmit() {
    this.autService.createUser(this.addForm.value)
      .subscribe( data => {
        this.dialogRef.close(data);
      });
  }

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      id: [],
      username: ['', Validators.required],
      password: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', Validators.required],
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
