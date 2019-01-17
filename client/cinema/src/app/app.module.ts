import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FilmsComponent } from './films/films.component';
import { SeancesComponent } from './films/seances/seances.component';
import { ReservationComponent } from './films/reservation/reservation.component';
import { ReservationAjouterComponent } from './films/reservation/reservation-ajouter/reservation-ajouter.component';
import { ReservationModifierComponent } from './films/reservation/reservation-modifier/reservation-modifier.component';
import { ReservationSupprimerComponent } from './films/reservation/reservation-supprimer/reservation-supprimer.component';
import { MatListModule } from "@angular/material";

@NgModule({
  declarations: [
    AppComponent,
    FilmsComponent,
    SeancesComponent,
    ReservationComponent,
    ReservationAjouterComponent,
    ReservationModifierComponent,
    ReservationSupprimerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatListModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
