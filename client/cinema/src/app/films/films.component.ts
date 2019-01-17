import { Component, OnInit } from '@angular/core';
import {RequetesHTTPService} from "./requetes-http.service";

@Component({
  selector: 'app-films',
  templateUrl: './films.component.html',
  styleUrls: ['./films.component.css']
})
export class FilmsComponent implements OnInit {

  /*public listeSeances = [
      {"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-22T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"7926cce5-6539-30e0-9628-8ce5ed1d8211"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-24T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"2e43f2d2-694e-3006-a403-9c578bcce52f"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-23T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"9e057d3b-ed4b-396b-bc6c-717204b9ec24"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-24T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"99850691-74d8-3579-9960-d9eb702a2ac9"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-22T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"ce388dd2-c9f2-3e97-9bdb-d4d41f97824c"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-21T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"a937a878-8f36-3923-84c3-2ea1a9ef351a"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-25T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"8a0c8cb4-beee-3ed5-9aa3-215b315e7960"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-19T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"a5dea677-6034-369d-8e2c-4c586890d75b"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-20T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"3e85a45e-a6cd-34b7-bdc8-d9608d3daecb"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-22T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"01bf701e-3679-3891-a086-6316893e085d"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-23T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"4c3df192-54e7-33db-9ba7-fa4b70de4f82"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-21T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"00ebfba2-4c27-38a7-b381-0939b588f4a6"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-21T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"6f6d6d2d-ae4a-3e22-ab81-c3839e60e3d6"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-19T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"099f0034-ea73-32a4-a9de-89d8eb70fbc2"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-22T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"f0278d08-2a3e-3d0b-b7d3-c4d5881df73a"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-22T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"e34d5e86-a267-3206-844b-e5f87f3c8101"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-25T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"6dd6b1a7-11d3-3f73-9740-8bf7c43fc37a"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-25T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"6e5f72f5-6560-35b6-9852-c11be834715b"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-23T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"cc23a162-113f-3ac6-a31a-0c9d41379f69"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-20T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"c32def72-239b-3ecb-a25e-5b8f7e3c5327"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-25T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"7cfba7cf-de2c-3951-9b5a-d07dd96a0930"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-19T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"0e771e43-0793-3cd5-857a-77fcee9fad9d"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-22T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"f1f316a7-cbdc-3124-9a6d-f114afce8ec6"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-24T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"159c519c-ae48-38d2-bb05-8d054c2bd8e6"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-23T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"41dde6d3-7ab7-32e0-a5d1-c77bed86fac8"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-23T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"34907639-65e3-33c7-84cb-cadc5dca3296"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-24T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"3aaec217-1b02-32a7-8c97-ea6cf8cf3e2a"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-19T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"9295a22d-2f21-3ccb-a8db-6d5321513a94"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-20T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"3379d512-6bb4-3649-a723-84a92e3d2980"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-19T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"e95e74e7-6fb8-3a4a-aa5b-c9b9240bf80e"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-23T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"97fbacb9-e9f2-378d-bc86-01f560416335"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-25T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"4341ebfb-773c-30a0-81f1-b79c403879db"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-22T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"8b4a957e-2e05-3c5d-a06c-a24e45b5ff1d"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-23T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"9c6ac81e-8a1b-31f5-b0fc-551930e445ab"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-25T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"c5eec83f-5042-3558-8a64-e51000394ff2"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-24T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"4ac6a167-71ce-3f3d-b43a-a76de58fa082"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-25T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"d1f2131f-eaae-3d56-8f15-1e02925c5093"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-23T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"5d950731-b985-345f-9dbd-98c775125036"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-21T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"1059b71f-8d5e-3a6d-b590-66051fb19813"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-24T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"14c3b871-f107-33f2-9e6f-1e780787d856"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-19T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"1afbbcff-ef8d-3e34-bcb6-5689d49d88b8"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-25T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"f78cbcd9-15a2-3e9f-ac34-ea089598025f"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-24T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"7a61b992-04f3-3346-b0ff-a6e2ba90890a"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-22T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"0d4dfb1a-6ad0-3870-b701-18770a7b7151"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-22T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"594bc2b6-c1fa-3901-bfe5-e686dbad45e1"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-19T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"521d6c64-fe74-3e58-9713-dd01fdbb2717"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-25T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"c71c5164-73cd-3c61-aca6-9c8fffbcbf11"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-20T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"52098a2e-9324-3fd2-b529-8afddff0928a"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-21T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"570a9db9-572f-361c-a3a2-10258e646bfa"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-21T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"627f4b2a-917b-3f63-832d-35a6d7e6df0f"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-20T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"44abc046-fc63-3fb5-a531-40125d0cdeec"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-21T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"2be7a019-05cf-31e3-8d22-5fe215b3a10b"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-21T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"4bc849fe-d087-3217-ba68-8e822df4a624"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-24T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"67a57569-8426-3c47-8ce6-1157ebbfc10c"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-20T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"011f02a1-fc9c-36a0-ab29-a85ef9f84c97"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-23T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"b2901b0e-96bf-3588-8676-83bcb0492bfc"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-21T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"0e8fd7da-5cb0-330a-87b7-2540fd75681d"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-23T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"15141512-ff7c-3d33-9026-5194388fe0dd"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-21T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"099b1f67-6ff5-3110-afbc-a4209de992d0"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-20T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"bb558605-d6c0-3b9a-a215-6212870db27f"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-23T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"d58c1dd1-5f1b-37b4-bdc4-bf310f96e6e7"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-22T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"10e9c54a-c072-33f7-88ac-383820fa23aa"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-24T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"0d6eed23-ccaf-36e4-9f2b-9203187c946d"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-20T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"acaecdfc-5e3a-3cf3-9283-9ac4ec388102"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-19T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"6d4883ea-2546-35b2-9b36-945e2a88bc95"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-24T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"4aff934b-a7a0-3ab9-8d2b-46a8bd561b72"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-20T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"43bfe954-8c01-3b5a-919b-2ff3022bbde2"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-24T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"ade1245b-491b-33a1-856a-fc40e7308e9d"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-19T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"18d1f670-ee50-33fb-967a-a356b1b119d5"},{"film":{"titre":"Burning","version":"VO","fiche":"https://www.imdb.com/title/tt7282468/?ref_=shtt_ov_tt","key":"Burning-VO"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-20T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"86451bcf-e819-35cd-b54c-f219e1b675e6"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-19T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"44c1ba8f-c803-30f9-9615-0240bc6f26ac"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-21T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"4a1977db-da76-37de-92f0-c60e98b20b7c"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-23T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"286a559b-b3d8-3828-aa9c-08b472c5273d"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-21T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"25117ee8-72cc-372b-8197-c1f095db8220"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-25T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"e770e966-0112-3608-a89f-1b1ac681e797"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-19T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"ce4de6ac-f66b-3d76-8a7d-77c3c2962e79"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-25T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"41c60830-e2f3-36a0-a181-9264c3b97a52"},{"film":{"titre":"Le poirier sauvage","version":"VF","fiche":"https://www.imdb.com/title/tt6628102/?ref_=shtt_ov_tt","key":"Le poirier sauvage-VF"},"salle":{"nom":"Salle 3","capacite":50},"date":"2018-09-22T16:00:00.000+0000","prix":10.0,"reservations":[],"id":"21afe010-3256-3f42-92f3-02a45a08caf3"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-24T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"bea35293-f80a-31ec-a671-64d3eb2bad9e"},{"film":{"titre":"Mission Impossible - Fallout","version":"VF","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VF"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-20T14:00:00.000+0000","prix":10.0,"reservations":[],"id":"9245ba0d-37de-3d38-9076-bc9c2f7ccf5e"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-22T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"f9f6a8e0-47d1-30b3-887d-a4853acfd29e"},{"film":{"titre":"Mission Impossible - Fallout","version":"VO","fiche":"https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt","key":"Mission Impossible - Fallout-VO"},"salle":{"nom":"Salle 1","capacite":100},"date":"2018-09-19T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"f62e35a2-1251-368b-9acf-dbcda1cfe3ea"},{"film":{"titre":"BlacKkKlansman","version":"VO","fiche":"https://www.imdb.com/title/tt7349662/?ref_=inth_ov_tt","key":"BlacKkKlansman-VO"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-20T20:00:00.000+0000","prix":10.0,"reservations":[],"id":"ae4d873c-7d88-3dc4-9957-0cd3228ded1c"},{"film":{"titre":"Operation Finale","version":"VF","fiche":"https://www.imdb.com/title/tt5208252/?ref_=inth_ov_tt","key":"Operation Finale-VF"},"salle":{"nom":"Salle 2","capacite":70},"date":"2018-09-25T18:00:00.000+0000","prix":10.0,"reservations":[],"id":"62c4c502-03f7-3435-be27-dbc768638bf0"}
  ]*/

  public listeFilm = null;
  public listeSeance = null;

  // Pour savoir si l'uteisateur s'est connecté
  public isLogged = 0;

  constructor(private httpService : RequetesHTTPService ) { }

  ngOnInit() {
    this.httpService.getFilm().subscribe(
      (data) => {
        if (data !== null){
          this.listeFilm = data;
        }else {
          console.log("Aucun film");
        }
      }
    );

    this.isLogged = 1
  }

  getSeances(idFilm) {
    this.httpService.getSeance(idFilm).subscribe(
      (data) => {
        if (data !== null) {
          this.listeSeance = data;
          console.log(this.listeSeance);
        }else {
          console.log("Aucune seance");
        }
      }
    )
  }

}
