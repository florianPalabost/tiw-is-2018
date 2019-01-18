import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { FilmsComponent } from "./films/films.component";
import { SeancesComponent } from "./films/seances/seances.component";
import { ReservationSupprimerComponent } from "./films/reservation/reservation-supprimer/reservation-supprimer.component";
import { ReservationModifierComponent } from "./films/reservation/reservation-modifier/reservation-modifier.component";
import { ReservationComponent } from "./films/reservation/reservation.component";
import { RequetesHTTPService } from "./films/requetes-http.service";

import { MatListModule, MatExpansionModule, MatButtonModule, MatNativeDateModule, MatIconModule, MatSidenavModule, MatToolbarModule, MatChipsModule, MatDividerModule, MatCardModule, MatGridListModule, MatTreeModule } from '@angular/material';
import { CommonModule } from "@angular/common";
import { LayoutModule } from '@angular/cdk/layout';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatTabsModule } from "@angular/material/tabs";

@NgModule({
  declarations: [
    AppComponent,
    FilmsComponent,
    SeancesComponent,
    ReservationComponent,
    ReservationModifierComponent,
    ReservationSupprimerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatListModule,
    HttpClientModule,
    CommonModule,
    MatButtonModule,
    MatToolbarModule,
    MatNativeDateModule,
    MatIconModule,
    MatTabsModule,
    MatSidenavModule,
    LayoutModule,
    BrowserAnimationsModule,
    MatExpansionModule,
    MatChipsModule,
    MatDividerModule,
    MatCardModule,
    MatGridListModule,
    MatTreeModule
  ],
  providers: [
    RequetesHTTPService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
