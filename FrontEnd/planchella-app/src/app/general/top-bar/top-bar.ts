import { Component } from '@angular/core';
import { SearchBoxMain } from '../../general/search-box-main/search-box-main';
import { ProfilePic } from "../profile-pic/profile-pic";

@Component({
  selector: 'top-bar',
  templateUrl: './top-bar.html',
  imports: [SearchBoxMain, ProfilePic],
  styleUrl: './top-bar.css',
  standalone: true
})
export class TopBar {


}

