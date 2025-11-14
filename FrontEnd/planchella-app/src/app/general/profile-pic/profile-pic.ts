import { Component, Input, Output } from '@angular/core';

@Component({
  selector: 'app-profile-pic',
  imports: [],
  templateUrl: './profile-pic.html',
  styleUrl: './profile-pic.css',
})
export class ProfilePic {
  @Input()
  href : string = "";

  @Input()
  hover_text: string = "";

}
