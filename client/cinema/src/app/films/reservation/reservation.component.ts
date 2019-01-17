import { Component, OnInit } from '@angular/core';
import {RequetesHTTPService} from "../requetes-http.service";

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {

  private email;
  listeReservation = [];

  constructor(private httpService : RequetesHTTPService ) { }

  ngOnInit() {
    /*this.httpService.getReservation(this.email).subscribe(
      (data) => {
        if (data !== null) {
          this.listeReservation = data;
        } else {
          console.log("Aucun film");
        }
      }
  )*/

  }

}
