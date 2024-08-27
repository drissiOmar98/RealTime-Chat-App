import {Component, ElementRef, inject, OnInit, ViewChild} from '@angular/core';
import {Conversation} from "../conversations/model/conversation.model";
import {ConversationService} from "../conversations/conversation.service";
import {SseService} from "./service/sse.service";
import {Oauth2AuthService} from "../auth/oauth2-auth.service";
import {ConnectedUser} from "../shared/model/user.model";
import {Message} from "../conversations/model/message.model";

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.scss'
})
export class MessagesComponent implements OnInit  {

  @ViewChild("messages") private messagesElement: ElementRef | undefined;

  conversation: Conversation | undefined;
  conversationService = inject(ConversationService);
  sseService = inject(SseService);
  oauth2Service = inject(Oauth2AuthService);

  private connectedUser: ConnectedUser | undefined;

  messagesByDate = new Map<string, Array<Message>>();

  hasInitBottomScroll = false;

  constructor() {

  }


  ngOnInit(): void {

  }



}
