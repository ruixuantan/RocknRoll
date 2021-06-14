import { Component } from '@angular/core';
import {Paths} from "../../paths";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  customPath = `/${Paths.customs}/`
  constructor() { }
}
