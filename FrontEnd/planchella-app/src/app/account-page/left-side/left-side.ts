import {Component, Input} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {User} from '../../models/user';

@Component({
  selector: 'app-left-side',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './left-side.html',
  styleUrl: './left-side.css',
})
export class LeftSide {
    @Input()
    user: User | undefined;

    // @Input()
    // user_name: string = "Karim Mohamed Basem"
    //
    // @Input()
    // user_bio: string = ""
}
