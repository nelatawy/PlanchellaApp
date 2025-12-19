import {EventSize, EventType} from './Enums';
import { EventAttachment } from './Event-Attachment';
import {EventAuthorData} from './event-author-data';


export interface EventData {
  id : string,
  eventType: EventType,
  eventSize : EventSize,
  authorData : EventAuthorData,
  title : string,
  description : string,
  creationDate: Date | string | number,
  upVotesCount : number,
  downVotesCount : number,
  eventStartDate: Date | string | number,
  eventEndDate: Date | string | number,
  attachments? : EventAttachment[]
}
