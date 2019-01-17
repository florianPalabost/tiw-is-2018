import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-reservation-modifier',
  templateUrl: './reservation-modifier.component.html',
  styleUrls: ['./reservation-modifier.component.scss']
})
export class ReservationModifierComponent implements OnInit, OnChanges {

  @Input() idReservation:any;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(change?: SimpleChanges) {
    console.log(change.idReservation.currentValue);
  }

}
