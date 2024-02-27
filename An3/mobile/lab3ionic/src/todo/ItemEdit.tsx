import React, { useContext, useEffect, useState } from 'react';
import {
  IonActionSheet,
  IonAlert,
  IonButton,
  IonButtons,
  IonContent,
  IonFab,
  IonFabButton,
  IonHeader,
  IonIcon,
  IonImg,
  IonInput,
  IonLoading,
  IonPage,
  IonTitle,
  IonToast,
  IonToolbar
} from '@ionic/react';
import { getLogger } from '../core';
import { camera, close, trash } from 'ionicons/icons';
import { ItemContext } from './ItemProvider';
import { RouteComponentProps } from 'react-router';
import { ItemProps } from './ItemProps';
import { getNetworkStatus } from '../useNetwork';
import { triggerSync } from '../syncApp';
import { getPreferences, writeQueueToPreferences } from '../usePreferences';
import { MyPhoto, usePhotos } from '../hooks/usePhotos';

import { useMyLocation } from '../hooks/useMyLocation';
import MyMap from '../hooks/MyMap';

const log = getLogger('ItemEdit');

interface ItemEditProps extends RouteComponentProps<{
  id?: string;
}> { }

const ItemEdit: React.FC<ItemEditProps> = ({ history, match }) => {
  const { items, saving, savingError, saveItem } = useContext(ItemContext);
  const [text, setText] = useState('');
  const [desc, setDesc] = useState('');
  const [item, setItem] = useState<ItemProps>();
  const [showAlert, setShowAlert] = useState(false);
  const [photo, setPhoto] = useState<MyPhoto>({ 'filepath': '', 'webviewPath': '' });

  const { photos, takePhoto, deletePhoto } = usePhotos();
  const [photoToDelete, setPhotoToDelete] = useState<MyPhoto>();

  const myLocation = useMyLocation();
  const { latitude: lat, longitude: lng } = myLocation.position?.coords || {}

  const [myLat, setMyLat] = useState<number>(0);
  const [myLng, setMyLng] = useState<number>(0);
  const [showMap, setShowMap] = useState(false);

  useEffect(() => {
    log('useEffect');
    const routeId = match.params.id || '';
    const item = items?.find(it => it._id === routeId);
    setItem(item);
    if (item) {
      setText(item.text);
      setDesc(item.desc);
      setPhoto(item.photo);
      setMyLat(item.myLat);
      setMyLng(item.myLng);
    }
    else {
      setMyLat(46.773084);
      setMyLng(23.621049);
    }
  }, [match.params.id, items]);
  const handleSave = () => {
    const editedItem = item ? { ...item, text, desc, photo, myLat, myLng } : { text, desc, photo, myLat, myLng };

    getNetworkStatus().then((status) => {
      if (status.connected) {
        saveItem && saveItem(editedItem).then(() => { history.goBack() });

      } else {
        getPreferences('queue').then((storedQueue) => {
          log('I got hereeee');
          if (storedQueue === null) {
            storedQueue = '[]';
          }
          // transform the item into a string
          const queueList = JSON.parse(storedQueue);
          queueList.push(editedItem);
          log('queueList', queueList);
          writeQueueToPreferences(queueList);
        });

        // write the queue to preferences

        log('callQueue written to preferences');
        setShowAlert(true);
        history.goBack();
      }
    });
  };

  log('render');


  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Edit</IonTitle>
          <IonButtons slot="end">
            <IonButton onClick={handleSave}>
              Save
            </IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonInput value={text} onIonChange={e => setText(e.detail.value || '')} />
        <IonInput value={desc} onIonChange={e => setDesc(e.detail.value || '')} />
        {/* // if photo is undefined, don't show anything; if it's defined, show the IonImg tag */}
        {photo.filepath !== '' ? <IonImg onClick={() => setPhotoToDelete(photo)} src={photo ? photo.webviewPath : undefined} style={{ width: '200px', height: '200px' }} /> : <div></div>}
        {myLat !== 0 && myLng !== 0 ? <MyMap startLat={46.773084}
          startLng={23.621049}
          markerLat={myLat}
          markerLng={myLng}
          onMapClick={(log('onMap'))}
          onMarkerClick={log('onMarker')} /> : <div></div>}
        <IonAlert
          isOpen={showAlert}
          onDidDismiss={() => setShowAlert(false)}
          header="Alert"
          message="Internet not available, item is saved locally and will sync when internet becomes available"
          buttons={['OK']}
        />
        {/* <IonLoading isOpen={saving} />
        {savingError && (
          <div>{savingError.message || 'Failed to save item'}</div>
        )} */}
        <IonFab vertical="bottom" horizontal="center" slot="fixed">
          <IonFabButton onClick={() => takePhoto().then((newPhoto) => { setPhoto(newPhoto) })}>
            <IonIcon icon={camera} />
          </IonFabButton>
        </IonFab>
        <IonActionSheet
          isOpen={!!photoToDelete}
          buttons={[{
            text: 'Delete',
            role: 'destructive',
            icon: trash,
            handler: () => {
              if (photoToDelete) {
                deletePhoto(photoToDelete);
                setPhotoToDelete(undefined);
                setPhoto({ 'filepath': '', 'webviewPath': '' })
              }
            }
          }, {
            text: 'Cancel',
            icon: close,
            role: 'cancel'
          }]}
          onDidDismiss={() => setPhotoToDelete(undefined)}
        />
      </IonContent>
    </IonPage>
  );
  function log(source: string) {
    return (e: any) => { console.log(e); setMyLat(e.latitude); setMyLng(e.longitude); };
  }
};

export default ItemEdit;
