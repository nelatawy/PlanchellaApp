export interface EventAttachment {
  id : string,
  fileName : string,
  fileType : string,
  sizeInBytes : number,
  downloadUrl? : string, // For direct download
}
