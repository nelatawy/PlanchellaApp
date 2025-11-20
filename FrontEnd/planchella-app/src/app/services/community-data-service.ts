import {Injectable} from '@angular/core';
import {CommunityData} from '../models/community-data';

@Injectable({providedIn : "root"})
export class CommunityDataService {
  async fetch_communities (count : number, username : string){
    const response : Response = await fetch(`http://localhost:8080/data/user/communities?count=${count}&username=${username}`);
    if (!response.ok){
      window.alert("Error fetching");
      return ;
    }
    const data = await response.json();
    return data as CommunityData[];
  }
}
