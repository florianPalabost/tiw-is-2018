import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequetesHTTPService {

  private urlConf = 'http://localhost:8080/';
  private urlOmdb = 'http://www.omdbapi.com/';
  //private reservationUrl = 'http://localhost:8091/'; // Pour TP9

  constructor(private http: HttpClient) { }

  // Recuperation de la liste totale des films
  getFilm() {
    //console.log(`${this.urlConf}cinema/films`);
    return this.http.get(`${this.urlConf}cinema/films`);
    //http://localhost:8080/backend/cinema/films
  }

  getFilmNom(idSeance) {
    return this.http.get(`${this.urlConf}cinema/seances/${idSeance}`);
  }

  // Recuperation des seances, lors du clic sur une ligne de la liste
  getSeance(idFilm) {
    return this.http.get(`${this.urlConf}cinema/films/${idFilm}/seances`);
  }

  // Recuperation des reservations
  getReservation(email) {
    return this.http.get(`${this.urlConf}cinema/users/${email}/reservations`);
    //http://localhost:8080/cinema/users/test@test.com/reservations
  }

  // Pour la partie Enrichissement : Récupération des informations des films depuis API OMDB
  getOmdb(idFilmImdb) {
    return this.http.get(`${this.urlOmdb}?i=${idFilmImdb}&apikey=8598fcba`);
    //return this.http.get('http://www.omdbapi.com/?i=tt6628102&apikey=8598fcba');
  }

  // Modification d'une reservation
  modifierReservation(utilisateur, idReservation, keySeance) : Observable<any> {
    // On a besoin de regarder queles parametres on a bespoin pour la reservation
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    // ATTENTION : Il faut ajouter le lien dans le backend
    return this.http.put(`${this.urlConf}reservations/${idReservation}`,
      JSON.stringify(
        {
          prenom: utilisateur.prenom,
          nom : utilisateur.nom,
          email : utilisateur.email,
          paye : false,
          seanceId : keySeance
        }) , httpOptions);
  }

  // Suppression d'une reservation
  supprimerReservation(utilisateur, idReservation, keySeance) : Observable<any> {
    // On a besoin de regarder queles parametres on a bespoin pour la reservation
    // ATTENTION : Il faut ajouter le lien dans le backend
    return this.http.delete(`${this.urlConf}reservations/${idReservation}`);
  }

  // Ajout d'une reservation
  addSeance(utilisateur, keyFilm, keySeance){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post(`${this.urlConf}cinema/films/${keyFilm}/seances/${keySeance}`,
      JSON.stringify(
        {
          prenom: utilisateur.prenom,
          nom : utilisateur.nom,
          email : utilisateur.email,
          paye : false,
          seanceId : keySeance
        }) , httpOptions);
  }

}
