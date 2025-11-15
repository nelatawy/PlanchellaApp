import {Component, input} from '@angular/core';
import {SlicePipe} from '@angular/common';
import {EventSize} from '../general/Enums';
import {ProfilePic} from '../general/profile-pic/profile-pic';


@Component({
  selector: 'app-event',
  imports: [SlicePipe, ProfilePic],
  templateUrl: './event.html',
  styleUrl: './event.css',
})
export class Event{

  EventSize = EventSize;

  eventSize : EventSize = EventSize.MID;

  title : string = 'Event';
  creationDate: string = '2 days ago';
  userName : string = 'Mazen';
  profilePicture : any = null;

  description: string = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";


  upVote : number = 0;
  downVote: number = 0;
  star: boolean = false;

}


