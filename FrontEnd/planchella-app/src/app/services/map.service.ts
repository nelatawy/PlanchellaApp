import { Injectable } from '@angular/core';
import * as L from 'leaflet';

@Injectable({
    providedIn: 'root',
})
export class MapService {
    private defaultIcon: L.Icon = L.icon({
        iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
        shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        tooltipAnchor: [16, -28],
        shadowSize: [41, 41],
    });

    constructor() {
        this.fixLeafletIcons();
    }

    /**
     * Initializes a Leaflet map.
     * @param elementId The ID of the HTML element where the map will be rendered.
     * @param center The initial center coordinates [latitude, longitude].
     * @param zoom The initial zoom level.
     * @returns The Leaflet Map instance.
     */
    initMap(elementId: string, center: [number, number] = [51.505, -0.09], zoom: number = 13): L.Map {
        const map = L.map(elementId).setView(center, zoom);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors',
        }).addTo(map);

        return map;
    }

    /**
     * Adds a marker to the map.
     * @param map The map instance to add the marker to.
     * @param latlng The coordinates [latitude, longitude].
     * @param popupContent Optional popup content.
     * @returns The Leaflet Marker instance.
     */
    addMarker(map: L.Map, latlng: [number, number], popupContent?: string): L.Marker {
        const marker = L.marker(latlng, { icon: this.defaultIcon }).addTo(map);

        if (popupContent) {
            marker.bindPopup(popupContent);
        }

        return marker;
    }

    /**
     * Attempts to get the user's current geolocation.
     * @returns A Promise that resolves to [latitude, longitude] or rejects if geolocation is unavailable.
     */
    getCurrentLocation(): Promise<[number, number]> {
        return new Promise((resolve, reject) => {
            if (!navigator.geolocation) {
                reject(new Error('Geolocation is not supported by your browser.'));
                return;
            }

            navigator.geolocation.getCurrentPosition(
                (position) => {
                    resolve([position.coords.latitude, position.coords.longitude]);
                },
                (error) => {
                    reject(new Error(`Geolocation failed: ${error.message}`));
                },
                {
                    enableHighAccuracy: true,
                    timeout: 5000,
                    maximumAge: 0,
                }
            );
        });
    }

    /**
     * Fixes the default Leaflet icon paths using CDN urls to ensure they load correctly.
     */
    private fixLeafletIcons(): void {
        const iconRetinaUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png';
        const iconUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png';
        const shadowUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png';

        L.Marker.prototype.options.icon = L.icon({
            iconRetinaUrl,
            iconUrl,
            shadowUrl,
            iconSize: [25, 41],
            iconAnchor: [12, 41],
            popupAnchor: [1, -34],
            tooltipAnchor: [16, -28],
            shadowSize: [41, 41],
        });
    }
}
