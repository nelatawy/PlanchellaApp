import { MimeType } from "./Enums";

export interface EventAttachment {
    id : string,
    fileName : string,
    mimeType : MimeType,
    size: number
}