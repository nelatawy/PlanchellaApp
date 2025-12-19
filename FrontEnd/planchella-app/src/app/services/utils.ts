import { MimeType } from "../models/Enums";

export class MimeTypeUtils {
  /**
   * Check if the MIME type is an image
   */
  static isImage(mimeType: string): boolean {
    return mimeType.startsWith('image/');
  }

  /**
   * Check if the MIME type is a video
   */
  static isVideo(mimeType: string): boolean {
    return mimeType.startsWith('video/');
  }

  /**
   * Check if the MIME type is audio
   */
  static isAudio(mimeType: string): boolean {
    return mimeType.startsWith('audio/');
  }

  /**
   * Check if the MIME type is a PDF
   */
  static isPdf(mimeType: string): boolean {
    return mimeType === MimeType.APPLICATION_PDF;
  }

  /**
   * Check if the MIME type is a document (Word, Excel, PowerPoint)
   */
  static isDocument(mimeType: string): boolean {
    return [
      MimeType.APPLICATION_MSWORD,
      MimeType.APPLICATION_DOCX,
      MimeType.APPLICATION_MSEXCEL,
      MimeType.APPLICATION_XLSX,
      MimeType.APPLICATION_MSPOWERPOINT,
      MimeType.APPLICATION_PPTX
    ].includes(mimeType as MimeType);
  }

  /**
   * Check if the MIME type is an archive/compressed file
   */
  static isArchive(mimeType: string): boolean {
    return [
      MimeType.APPLICATION_ZIP,
      MimeType.APPLICATION_RAR,
      MimeType.APPLICATION_7Z,
      MimeType.APPLICATION_TAR,
      MimeType.APPLICATION_GZIP
    ].includes(mimeType as MimeType);
  }

  /**
   * Get file extension from MIME type
   */
  static toFileExtension(mimeType: string): string {
    const mimeToExt: Record<string, string> = {
      // Text and Document Types
      [MimeType.TEXT_PLAIN]: 'txt',
      [MimeType.TEXT_CSV]: 'csv',
      [MimeType.TEXT_HTML]: 'html',
      [MimeType.TEXT_CSS]: 'css',
      [MimeType.APPLICATION_PDF]: 'pdf',
      [MimeType.APPLICATION_MSWORD]: 'doc',
      [MimeType.APPLICATION_DOCX]: 'docx',
      [MimeType.APPLICATION_MSEXCEL]: 'xls',
      [MimeType.APPLICATION_XLSX]: 'xlsx',
      [MimeType.APPLICATION_MSPOWERPOINT]: 'ppt',
      [MimeType.APPLICATION_PPTX]: 'pptx',

      // Image Types
      [MimeType.IMAGE_JPEG]: 'jpg',
      [MimeType.IMAGE_PNG]: 'png',
      [MimeType.IMAGE_GIF]: 'gif',
      [MimeType.IMAGE_WEBP]: 'webp',
      [MimeType.IMAGE_SVG]: 'svg',
      [MimeType.IMAGE_TIFF]: 'tif',

      // Compressed and Archive Types
      [MimeType.APPLICATION_ZIP]: 'zip',
      [MimeType.APPLICATION_RAR]: 'rar',
      [MimeType.APPLICATION_7Z]: '7z',
      [MimeType.APPLICATION_TAR]: 'tar',
      [MimeType.APPLICATION_GZIP]: 'gz',

      // Audio and Video Types
      [MimeType.AUDIO_MPEG]: 'mp3',
      [MimeType.AUDIO_WAV]: 'wav',
      [MimeType.AUDIO_OGG]: 'ogg',
      [MimeType.VIDEO_MP4]: 'mp4',
      [MimeType.VIDEO_QUICKTIME]: 'mov',
      [MimeType.VIDEO_AVI]: 'avi',

      // Application and Data Types
      [MimeType.APPLICATION_JSON]: 'json',
      [MimeType.APPLICATION_XML]: 'xml'
    };

    return mimeToExt[mimeType] || 'bin';
  }

  /**
   * Get MIME type from file extension
   */
  static fromFileExtension(extension: string): MimeType {
    const ext = extension.toLowerCase().replace('.', '');
    
    const extToMime: Record<string, MimeType> = {
      // Text and Document Types
      'txt': MimeType.TEXT_PLAIN,
      'csv': MimeType.TEXT_CSV,
      'html': MimeType.TEXT_HTML,
      'css': MimeType.TEXT_CSS,
      'pdf': MimeType.APPLICATION_PDF,
      'doc': MimeType.APPLICATION_MSWORD,
      'docx': MimeType.APPLICATION_DOCX,
      'xls': MimeType.APPLICATION_MSEXCEL,
      'xlsx': MimeType.APPLICATION_XLSX,
      'ppt': MimeType.APPLICATION_MSPOWERPOINT,
      'pptx': MimeType.APPLICATION_PPTX,

      // Image Types
      'jpg': MimeType.IMAGE_JPEG,
      'jpeg': MimeType.IMAGE_JPEG,
      'png': MimeType.IMAGE_PNG,
      'gif': MimeType.IMAGE_GIF,
      'webp': MimeType.IMAGE_WEBP,
      'svg': MimeType.IMAGE_SVG,
      'tif': MimeType.IMAGE_TIFF,
      'tiff': MimeType.IMAGE_TIFF,

      // Compressed and Archive Types
      'zip': MimeType.APPLICATION_ZIP,
      'rar': MimeType.APPLICATION_RAR,
      '7z': MimeType.APPLICATION_7Z,
      'tar': MimeType.APPLICATION_TAR,
      'gz': MimeType.APPLICATION_GZIP,

      // Audio and Video Types
      'mp3': MimeType.AUDIO_MPEG,
      'wav': MimeType.AUDIO_WAV,
      'ogg': MimeType.AUDIO_OGG,
      'mp4': MimeType.VIDEO_MP4,
      'mov': MimeType.VIDEO_QUICKTIME,
      'avi': MimeType.VIDEO_AVI,

      // Application and Data Types
      'json': MimeType.APPLICATION_JSON,
      'xml': MimeType.APPLICATION_XML
    };

    return extToMime[ext] || MimeType.APPLICATION_OCTET_STREAM;
  }

  /**
   * Get a user-friendly icon for the MIME type
   */
  static getIcon(mimeType: string): string {
    if (this.isImage(mimeType)) return '🖼️';
    if (this.isPdf(mimeType)) return '📄';
    if (this.isVideo(mimeType)) return '🎥';
    if (this.isAudio(mimeType)) return '🎵';
    if (mimeType.includes('word')) return '📝';
    if (mimeType.includes('excel') || mimeType.includes('spreadsheet')) return '📊';
    if (mimeType.includes('powerpoint') || mimeType.includes('presentation')) return '📽️';
    if (this.isArchive(mimeType)) return '🗜️';
    if (mimeType === MimeType.TEXT_HTML) return '🌐';
    if (mimeType === MimeType.TEXT_CSS) return '🎨';
    if (mimeType === MimeType.APPLICATION_JSON) return '{ }';
    if (mimeType === MimeType.APPLICATION_XML) return '< >';
    return '📎';
  }

  /**
   * Get a user-friendly category name
   */
  static getCategory(mimeType: string): string {
    if (this.isImage(mimeType)) return 'Image';
    if (this.isVideo(mimeType)) return 'Video';
    if (this.isAudio(mimeType)) return 'Audio';
    if (this.isPdf(mimeType)) return 'PDF Document';
    if (this.isDocument(mimeType)) return 'Document';
    if (this.isArchive(mimeType)) return 'Archive';
    if (mimeType.startsWith('text/')) return 'Text File';
    return 'File';
  }

  /**
   * Format file size in human-readable format
   */
  static formatSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    
    return Math.round((bytes / Math.pow(k, i)) * 100) / 100 + ' ' + sizes[i];
  }

  /**
   * Check if the file type can be previewed in browser
   */
  static canPreview(mimeType: string): boolean {
    return this.isImage(mimeType) || 
           this.isPdf(mimeType) || 
           this.isVideo(mimeType) || 
           this.isAudio(mimeType);
  }
}