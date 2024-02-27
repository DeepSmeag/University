import React from 'react';
import { IonButton, IonImg, IonItem, IonLabel, IonModal, createAnimation } from '@ionic/react';
import { ItemProps } from './ItemProps';
import type { Animation } from '@ionic/react';
import { infinite } from 'ionicons/icons';

interface ItemPropsExt extends ItemProps {
  onEdit: (_id?: string) => void;
}

const Item: React.FC<ItemPropsExt> = ({ _id, text, desc, photo, onEdit }) => {
  const cardEl = React.useRef<HTMLIonItemElement>(null);
  const animation = React.useRef<Animation>();
  const colorAnimation = React.useRef<Animation>();
  const flickerAnimation = (baseEl: any) => {

    const flickerKeyframes = [
      { offset: 0, opacity: '0' },
      { offset: 0.2, opacity: '1' },
      { offset: 0.4, opacity: '0' },
      { offset: 0.6, opacity: '1' },
      { offset: 0.8, opacity: '0' },
      { offset: 1, opacity: '1' }
    ];

    animation.current = createAnimation()
      .addElement(baseEl)
      .easing('ease-out')
      .duration(1000) // Adjust the duration as needed
      .iterations(Infinity) // Infinite loop for flickering effect
      .keyframes(flickerKeyframes);
  };
  const colorCyclingAnimation = (baseEl: any) => {
    const colorCyclingKeyframes = [
      { offset: 0, background: 'red' },
      { offset: 0.33, background: 'pink' },
      { offset: 0.66, background: 'purple' },
      { offset: 1, background: 'red' },
    ];

    colorAnimation.current = createAnimation()
      .addElement(baseEl)
      .easing('linear') // You can adjust the easing function
      .duration(3000) // Adjust the duration as needed
      .iterations(Infinity) // Infinite loop for color cycling effect
      .keyframes(colorCyclingKeyframes);
  };


  return (
    <IonItem ref={cardEl} style={{ '--background': 'transparent' }} onClick={() => onEdit(_id)}
      onPointerEnter={() => { colorCyclingAnimation(cardEl.current); colorAnimation.current?.play() }}
      onPointerLeave={() => colorAnimation.current?.stop()} >
      {/* onClick={() => onEdit(_id)} */}
      {/* <IonModal isOpen={showModal} enterAnimation={enterAnimation} leaveAnimation={leaveAnimation}>
        <p>This is modal content</p>
        <IonButton onClick={() => cardAnimation.current?.play()}>Close Modal</IonButton>
      </IonModal>
      <IonButton onClick={() => cardAnimation.current?.play()}>Show Modal</IonButton> */}
      <IonLabel>{text}</IonLabel>
      <IonLabel>{desc}</IonLabel>

      {photo.filepath !== '' ? <IonImg src={photo.webviewPath} style={{ width: '100px', height: '100px' }} /> : null}
    </IonItem >
  );
};

export default Item;
