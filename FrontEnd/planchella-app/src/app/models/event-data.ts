
import {EventSize, EventType} from '../general/Enums';
import {EventAuthorData} from './event-author-data';


export interface EventData {
  eventType: EventType,
  eventSize : EventSize,
  authorData : EventAuthorData,
  title : string,
  description : string,
  creationDate: Date | string | number
}
