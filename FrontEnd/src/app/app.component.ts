import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {fontAwesomeIcons} from "./shared/font-awesome-icons.";
import {Oauth2AuthService} from "./auth/oauth2-auth.service";
import {NavbarComponent} from "./layout/navbar/navbar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, FaIconComponent,NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent  implements OnInit {
  title = 'Real-Time Chat App';
  private faIconLibrary = inject(FaIconLibrary);
  private oauth2Service = inject(Oauth2AuthService);


  ngOnInit(): void {
    this.initFontAwesome();
    this.initAuthentication();
  }

  private initAuthentication(): void {
    this.oauth2Service.initAuthentication();
  }

  private initFontAwesome() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }
}
