import { Component } from '@angular/core';
import {LeftSide} from './left-side/left-side';
import {TopBar} from '../general/top-bar/top-bar';
import {RightSide} from './right-side/right-side';
import {User} from '../models/user';

@Component({
  selector: 'app-account-page',
  imports: [
    LeftSide,
    TopBar,
    RightSide
  ],
  templateUrl: './account-page.html',
  styleUrl: './account-page.css',
})
export class AccountPage {

  // Mockup user data to be passed to the page
  user: User = {
    "name": "Karim Mohamed Basem",
    "bio": "CSED Student at Alexandria University",
    "education": "Faculty of Engineering, Alexandria University",
    "address": "Alexandria, Egypt",
    "email": "karimbasem14@gmail.com"
  }



}
