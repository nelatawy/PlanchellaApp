import { Component, Input, Output } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-profile-pic',
  imports: [
    RouterLink
  ],
  templateUrl: './profile-pic.html',
  styleUrl: './profile-pic.css',
})
export class ProfilePic {

  private _pic_href : string = "/profile_placeholder.png";
  private _href : string = "";

  @Input()
  set href(value : string) {
    if(value != "" ) this._href = value;
  }

  get href() {
    return this._href;
  }

  @Input()
  hover_text: string = "";

  @Input()
  set pic_href(value : string) {
    if(value != "" ) this._pic_href = value;
  }

  get pic_href() {
    return this._pic_href;
  }
}
