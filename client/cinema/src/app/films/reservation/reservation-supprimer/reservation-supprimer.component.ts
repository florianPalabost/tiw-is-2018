import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-reservation-supprimer',
  templateUrl: './reservation-supprimer.component.html',
  styleUrls: ['./reservation-supprimer.component.scss']
})
export class ReservationSupprimerComponent implements OnInit, OnChanges{

  @Input() idReservation:any;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(change?: SimpleChanges) {
    //console.log(change.idReservation.currentValue);
  }

}
