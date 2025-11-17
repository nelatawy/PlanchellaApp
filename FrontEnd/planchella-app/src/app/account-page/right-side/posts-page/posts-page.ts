import { Component } from '@angular/core';
import {EmptyPage} from '../empty-page/empty-page';

@Component({
  selector: 'app-posts-page',
  imports: [
    EmptyPage
  ],
  templateUrl: './posts-page.html',
  styleUrl: './posts-page.css',
})
export class PostsPage {
  post = [];

}
