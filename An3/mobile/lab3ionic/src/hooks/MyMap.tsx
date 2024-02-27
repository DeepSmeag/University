import { GoogleMap } from '@capacitor/google-maps';
import { useEffect, useRef } from 'react';
import { mapsApiKey } from '../../mapsApiKey';

interface MyMapProps {
  startLat: number;
  startLng: number;
  markerLat: number;
  markerLng: number;
  onMapClick: (e: any) => void,
  onMarkerClick: (e: any) => void,
}

const MyMap: React.FC<MyMapProps> = ({ startLat, startLng, markerLat, markerLng, onMapClick, onMarkerClick }) => {
  const mapRef = useRef<HTMLElement>(null);
  const markerRef = useRef<string>('');
  const armed = useRef<number>(0);
  const mapG = useRef<GoogleMap>();
  useEffect(myMapEffect, [mapRef.current])

  return (
    <div className="component-wrapper">
      <capacitor-google-map ref={mapRef} style={{
        display: 'block',
        width: 300,
        height: 400
      }}></capacitor-google-map>
    </div>
  );


  function myMapEffect() {
    let canceled = false;
    let googleMap: GoogleMap | null = null;
    createMap();
    mapG.current = googleMap!;
    return () => {
      canceled = true;
      googleMap?.removeAllMapListeners();
    }

    async function createMap() {
      if (!mapRef.current) {
        return;
      }

      googleMap = await GoogleMap.create({
        id: 'my-cool-map',
        element: mapRef.current,
        apiKey: mapsApiKey,
        config: {
          center: { lat: markerLat == 0 ? startLat : markerLat, lng: markerLng == 0 ? startLng : markerLng },
          zoom: 16
        }
      })
      console.log('gm created');

      if (armed.current == 2) {
        armed.current = 0;
        console.log(markerLat, markerLng);
        await googleMap.addMarker({ coordinate: { lat: markerLat == 0 ? startLat : markerLat, lng: markerLng == 0 ? startLng : markerLng }, title: 'My location' }).then((markerId) => {
          markerRef.current = markerId;
          console.log('new marker is ', markerId, ' and markerRef is ', markerRef.current);
        }).catch((err) => {
          console.log(err);
        });
        console.log(armed.current);
      }
      console.log('armed is ', armed.current);
      armed.current++;
      // else {
      //   armed.current++;
      // }
      // if (armed.current == 3) {
      //   armed.current = 0;
      // }
      // const myLocationMarkerId = await googleMap.addMarker({ coordinate: { lat, lng }, title: 'My location2' });
      await googleMap.setOnMapClickListener(({ latitude, longitude }) => {
        console.log('armed is ', armed.current);
        if (armed.current == 0) {
          onMapClick({ latitude, longitude });
          if (markerRef.current !== '') {
            console.log('deleting markerRef is ', markerRef.current);
            googleMap?.removeMarker(markerRef.current).then(() => {
              googleMap?.addMarker({ coordinate: { lat: latitude, lng: longitude }, title: 'My location' }).then((markerId) => {
                markerRef.current = markerId;
                console.log('new marker is ', markerId, ' and markerRef is ', markerRef.current);
              });
            });
          }
          else {
            googleMap?.addMarker({ coordinate: { lat: latitude, lng: longitude }, title: 'My location' }).then((markerId) => {
              markerRef.current = markerId;
              console.log('new marker is ', markerId, ' and markerRef is ', markerRef.current);
            });
          }
          armed.current += 1;
        }
        else {
          armed.current += 1;
          if (armed.current >= 3) {
            armed.current = 0;
          }
        }
      });
      await googleMap.setOnMarkerClickListener(({ markerId, latitude, longitude }) => {
        onMarkerClick({ markerId, latitude, longitude });
      });
    }
  }
}

export default MyMap;
