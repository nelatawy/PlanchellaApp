import {EventData} from '../models/event-data';
import {Injectable} from '@angular/core';

@Injectable({providedIn : "root"})
export class EventDataService {
  async fetch_events (count : number, communityName : string){
    const response : Response = await fetch(`http://localhost:8080/data/community/events?count=${count}&communityName=${communityName}`);
    if (!response.ok){
      window.alert("Error fetching");
      return ;
    }
    const data = await response.json();
    return data as EventData[];
  }
}
