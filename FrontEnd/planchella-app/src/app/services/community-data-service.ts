import {Injectable} from '@angular/core';
import {CommunityData} from '../models/community-data';

@Injectable({providedIn : "root"})
export class CommunityDataService {

  private cache: Map<string, CommunityData> = new Map();

  async fetch_communities (count : number, username : string){
    const response : Response = await fetch(`http://localhost:8080/data/user/communities?count=${count}&username=${username}`);
    if (!response.ok){
      window.alert("Error fetching");
      return ;
    }
    const data: CommunityData[] = await response.json();

    this.cache.clear();
    for(let item of data) {
      this.cache.set(item.name, item);
    }

    return data as CommunityData[];
  }

  public get_community(name: string): CommunityData | undefined {
    return this.cache.get(name);
  }
}
