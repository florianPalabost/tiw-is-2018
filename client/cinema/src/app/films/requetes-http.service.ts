import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RequetesHTTPService {

  private urlConf = 'http://localhost:8080/';
  private urlConf1 = 'http://localhost:8091/';
  private urlOmdb = 'http://www.omdbapi.com/';

  constructor(private http: HttpClient) { }

  getFilm() {
    //console.log(`${this.urlConf}cinema/films`);
    return this.http.get(`${this.urlConf}cinema/films`);
    //http://localhost:8080/backend/cinema/films
  }

  getFilmNom(idSeance) {
    return this.http.get(`${this.urlConf}cinema/seances/${idSeance}`);
  }

  getSeance(idFilm) {
    return this.http.get(`${this.urlConf}cinema/films/${idFilm}/seances`);
  }

  getReservation(email) {
    return this.http.get(`${this.urlConf1}cinema/users/${email}/reservations`);
    //http://localhost:8080/cinema/users/test@test.com/reservations
  }

  // Pour la partie Enrichissement : Récupération des informations des films depuis API OMDB
  getOmdb(idFilmImdb) {
    return this.http.get(`${this.urlOmdb}?i=${idFilmImdb}&apikey=8598fcba`);
    //return this.http.get('http://www.omdbapi.com/?i=tt6628102&apikey=8598fcba');
  }

  modifierReservation(idReservation) {
    // A Faire
    //return this.http.get(`${this.urlOmdb}?i=${idReservation}&apikey=8598fcba`);
  }

  effacerReservation(idReservation) {
    // A Faire
    //return this.http.get(`${this.urlOmdb}?i=${idReservation}&apikey=8598fcba`);
  }

  addSeance(utilisateur, keyFilm, keySeance){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    console.log('film key : '+keyFilm);
    console.log('seance key :'+keySeance);
    return this.http.post(`${this.urlConf1}cinema/films/${keyFilm}/seances/${keySeance}`,
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
