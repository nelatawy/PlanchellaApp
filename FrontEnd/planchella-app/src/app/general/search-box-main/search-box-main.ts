import { Component } from '@angular/core';

@Component({
  selector: 'search-box-main',
  templateUrl: './search-box-main.html',
  styleUrl: './search-box-main.css',
  standalone: true
})
export class SearchBoxMain {
  search(input : string) {

    window.alert(input);
  }
}
