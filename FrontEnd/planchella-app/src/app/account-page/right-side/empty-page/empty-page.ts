import {Component, Input} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-empty-page',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './empty-page.html',
  styleUrl: './empty-page.css',
})
export class EmptyPage {

  @Input()
  iconSrc: string | undefined;

  @Input()
  title: string | undefined;

  @Input()
  sub_title: string | undefined;
}
