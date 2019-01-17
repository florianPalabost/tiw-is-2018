import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RequetesHTTPService {

  private urlConf = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  getFilm() {
    //console.log(`${this.urlConf}cinema/films`);
    return this.http.get(`${this.urlConf}cinema/films`);
    //http://localhost:8080/backend/cinema/films
  }

  getSeance(idFilm) {
    return this.http.get(`${this.urlConf}cinema/films/${idFilm}/seances`);
  }

  getReservation(email) {
    // Ajouter au Backend
    //return this.http.get(`${this.urlConf}cinema/reservations/${email}`);
    return [];
  }

}
