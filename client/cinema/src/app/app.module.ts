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

import {
  MatListModule,
  MatExpansionModule,
  MatButtonModule,
  MatNativeDateModule,
  MatIconModule,
  MatSidenavModule,
  MatToolbarModule,
  MatChipsModule,
  MatDividerModule,
  MatCardModule,
  MatGridListModule,
  MatTreeModule,
  MatDialogModule, MatSnackBarModule
} from '@angular/material';
import { CommonModule } from "@angular/common";
import { LayoutModule } from '@angular/cdk/layout';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatTabsModule } from "@angular/material/tabs";
import { AuthentificationComponent, DialogInscription, DialogLogin } from './authentification/authentification.component';
import { AuthentificationService } from "./authentification/authentification.service";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    FilmsComponent,
    SeancesComponent,
    ReservationComponent,
    ReservationModifierComponent,
    ReservationSupprimerComponent,
    AuthentificationComponent,
    DialogLogin,
    DialogInscription
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
    MatTreeModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule
  ],
  providers: [
    RequetesHTTPService,
    AuthentificationService
  ],
  bootstrap: [AppComponent],
  entryComponents: [DialogLogin,
  DialogInscription]
})
export class AppModule { }
