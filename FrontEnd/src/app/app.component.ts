import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {fontAwesomeIcons} from "./shared/font-awesome-icons.";
import {Oauth2AuthService} from "./auth/oauth2-auth.service";
import {NavbarComponent} from "./layout/navbar/navbar.component";
import {ToastService} from "./shared/toast/toast.service";
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import {ConversationsComponent} from "./conversations/conversations.component";
import {HeaderComponent} from "./layout/header/header.component";
import {SendComponent} from "./messages/send/send.component";
import {MessagesComponent} from "./messages/messages.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, FaIconComponent, NavbarComponent, NgbToast, ConversationsComponent, HeaderComponent, SendComponent, MessagesComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent  implements OnInit {
  title = 'Real-Time Chat App';
  private faIconLibrary = inject(FaIconLibrary);
  private oauth2Service = inject(Oauth2AuthService);
  toastService = inject(ToastService);


  ngOnInit(): void {
    this.initFontAwesome();
    this.initAuthentication();
    this.configDayJs();
  }

  private initAuthentication(): void {
    this.oauth2Service.initAuthentication();
  }

  private initFontAwesome() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  private configDayJs() {
    dayjs.extend(relativeTime);
  }
}
