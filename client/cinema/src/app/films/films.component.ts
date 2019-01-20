import { Component, OnInit } from '@angular/core';
import {RequetesHTTPService} from "./requetes-http.service";
import {AuthentificationService} from "../authentification/authentification.service";

@Component({
  selector: 'app-films',
  templateUrl: './films.component.html',
  styleUrls: ['./films.component.css']
})
export class FilmsComponent implements OnInit {

  public listeFilm = null;
  public listeSeance = null;

  // omDbData pour la partie Enrichissement
  omdbData = [];

  // Pour savoir si l'uteisateur s'est connecté
  public isLogged = 0;

  constructor(private httpService : RequetesHTTPService, private authService : AuthentificationService) {

    this.authService.connectionSubscription.subscribe(
      () => {
        this.isConnected()
      }
    )
  }

  ngOnInit() {
    console.log('init film');
    this.httpService.getFilm().subscribe(
      (data) => {
        if (data !== null){
          this.listeFilm = data;
          // Pour la partie Enrichissement : setOmdbData
          this.setOmdbData(this.listeFilm);
        }else {
          console.log("Aucun film");
        }
      }
    );
  }

  isConnected(){
    if(window.sessionStorage.getItem('token')){
      this.isLogged = 1
    }else{
      this.isLogged = 0
    }
  }

  getSeances(idFilm) {
    this.listeSeance = [];
    this.httpService.getSeance(idFilm).subscribe(
      (data) => {
        if (data !== null) {
          this.listeSeance = data;
          //console.log(this.listeSeance);
        }else {
          console.log("Aucune seance");
        }
      }
    )
  }

  // depuis seance component en output
  ajouterUneSeance(eltSeance){
    console.log(this.authService.getUtilisateur());
    this.httpService.addSeance(this.authService.getUtilisateur(), eltSeance.film.key, eltSeance.id).subscribe(
      (data) => {
        // dans le cas ou la reservation est ok
        this.getSeances(eltSeance.film.key);
      }
    );
  }

  // ---- Partie Enrichissement ----
  setOmdbData(movieL) {
    movieL = this.listeFilm;
    for (let movie of movieL) {
      this.httpService.getOmdb(movie.fiche.slice(27, 36)).subscribe(
        data => {
          if(data !== null) {
            this.omdbData.push({key: movie.key, data: data});
          }else {
            console.log("Pas des informations omdb");
          }
        }
      );
    }
  }

  getOmdbData(id) {
    for (let omdbd of this.omdbData) {
      if (omdbd.key === id) {
        return omdbd.data;
      }
    }
  }

}
