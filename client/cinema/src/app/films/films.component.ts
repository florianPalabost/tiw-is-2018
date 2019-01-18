import { Component, OnInit } from '@angular/core';
import {RequetesHTTPService} from "./requetes-http.service";

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

  constructor(private httpService : RequetesHTTPService ) { }

  ngOnInit() {
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

    this.isLogged = 1;
  }

  getSeances(idFilm) {
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
