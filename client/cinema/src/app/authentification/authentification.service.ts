import {Injectable, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Subject} from "rxjs";

@Injectable()
export class AuthentificationService implements OnInit{

  public currentUser;
  public currentUserTest  = {prenom:'PRENOM', nom:'NOM', email:'test@test.com'};

  private urlConf = 'http://localhost:8080/users/';
  public connectionSubscription = new Subject();

  constructor(private http: HttpClient) {
  }

  ngOnInit(){
    //this.currentUser
  }

  getUtilisateur(){
    if(this.currentUser) {
      return this.currentUser;
    }else {
      return this.currentUserTest;
    }
  }

  createUser(utilisateur){
    return this.http.post(this.urlConf, utilisateur);
  }

  login(param1) {
    //console.log(`${this.urlConf}cinema/films`);
    //return this.http.get(`${this.urlConf}cinema/films`);
    //http://localhost:8080/backend/cinema/films
  }
}
