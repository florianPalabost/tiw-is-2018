import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-seances',
  templateUrl: './seances.component.html',
  styleUrls: ['./seances.component.css']
})
export class SeancesComponent implements OnInit, OnChanges {

  @Input() listeSeance:any;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(change?: SimpleChanges) {
    //console.log(change);
    this.listeSeance = change.listeSeance.currentValue;
  }

  onClickReserver(idSeance)  {
    console.log(idSeance)
  }

}
