import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {RequetesHTTPService} from "../requetes-http.service";

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit, OnChanges {

  @Input() listeSeance:any;
  @Output() addSeance = new EventEmitter();

  constructor(private httpService : RequetesHTTPService ) { }

  ngOnInit() {
  }

  ngOnChanges(change?: SimpleChanges) {
    //console.log(change);
    this.listeSeance = change.listeSeance.currentValue;
  }

  onClickReserver(eltSeance) {
    if (eltSeance !== null) {
      this.addSeance.emit(eltSeance)


    }
  }
}
