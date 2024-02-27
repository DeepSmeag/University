import React, { useContext, useState, useEffect } from 'react';
import { RouteComponentProps } from 'react-router';
import {
  IonContent,
  IonFab,
  IonFabButton,
  IonButton,
  IonHeader,
  IonIcon,
  IonList, IonLoading,
  IonPage,
  IonTitle,
  IonToolbar,
  IonRow,
  IonCol,
  IonInfiniteScrollContent,
  IonInfiniteScroll,
  IonSearchbar,
  IonSelectOption,
  IonSelect,
  createAnimation
} from '@ionic/react';
import { add, settingsOutline } from 'ionicons/icons';
import Item from './Item';
import { getLogger } from '../core';
import { ItemContext } from './ItemProvider';
import { AuthContext } from '../auth';
import { ItemProps } from './ItemProps';
const log = getLogger('ItemList');

const ItemList: React.FC<RouteComponentProps> = ({ history }) => {
  const { items, fetching, fetchingError } = useContext(ItemContext);
  const { isAuthenticated, isAuthenticating, login, authenticationError, logout } = useContext(AuthContext);


  const [itemsToDisplay, setItemsToDisplay] = useState<ItemProps[]>([]); // Items to display on the page
  const [itemsPerPage, setItemsPerPage] = useState(15); // Number of items per page
  const [currentPage, setCurrentPage] = useState(1); // Current page
  const [scrollRefresh, setScrollRefresh] = useState(false); // Current page

  // const [filteredItems, setFilteredItems] = useState<ItemProps[]>([]); // Items to display on the page
  const [searchQuery, setSearchQuery] = useState('');
  const [filterText, setFilterText] = useState('');

  const cardEl = React.useRef<HTMLIonTitleElement>(null);
  const buttonEl = React.useRef<HTMLIonButtonElement>(null);
  const buttonAnimation = React.useRef<Animation>();
  const colorCyclingAnimation = (baseEl: any) => {
    const colorCyclingKeyframes = [
      { offset: 0, color: 'red' },
      { offset: 0.33, color: 'pink' },
      { offset: 0.66, color: 'purple' },
      { offset: 1, color: 'red' },
    ];

    return createAnimation()
      .addElement(baseEl)
      .easing('linear') // You can adjust the easing function
      .duration(3000) // Adjust the duration as needed
      .iterations(Infinity) // Infinite loop for color cycling effect
      .keyframes(colorCyclingKeyframes);
  };
  const waveColorAnimation = (baseEl: any) => {
    const waveColorKeyframes = [
      { offset: 0, '--background': 'red', transform: 'rotate(0deg)' },
      { offset: 0.25, '--background': 'yellow', transform: 'rotate(90deg)' },
      { offset: 0.5, '--background': 'green', transform: 'rotate(180deg)' },
      { offset: 0.75, '--background': 'blue', transform: 'rotate(270deg)' },
      { offset: 1, '--background': 'purple', transform: 'rotate(360deg)' },
    ];

    return createAnimation()
      .addElement(baseEl)
      .easing('linear') // You can adjust the easing function
      .duration(4000) // Adjust the duration as needed
      .iterations(Infinity) // Infinite loop for the wave-like effect
      .keyframes(waveColorKeyframes) as any;
  };
  useEffect(() => {
    colorCyclingAnimation(cardEl.current).play();
  }, [cardEl]);
  useEffect(() => {
    buttonAnimation.current = waveColorAnimation(buttonEl.current);
  }, [buttonEl]);

  // Load initial items
  useEffect(() => {
    if (items) {
      loadItemsForPage(currentPage, true);
    }

  }, [items]);
  useEffect(() => {
    if (items) {
      // print values for query, items, itemsToDisplay, currentPage
      console.log('QUERY --- ', searchQuery);
      console.log('CURRENTPAGE --- ', currentPage);
      loadItemsForPage(currentPage, false);

    }
  }, [currentPage, searchQuery]);

  // Function to load items for a specific page
  const loadItemsForPage = (page: number, newBatch: boolean) => {
    // console.log("loadItemsForPage----------------------");
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    if (searchQuery === '') {

      var filteredItems = items!;
      console.log(filteredItems);
    }
    else {
      if (filterText === 'desc') {

        var filteredItems = items!.filter(item => item.desc.toLowerCase().includes(searchQuery.toLowerCase()));
      }
      else {
        var filteredItems = items!.filter(item => item.text.toLowerCase().includes(searchQuery.toLowerCase()));
      }
    }
    if (newBatch) {
      setItemsToDisplay(filteredItems.slice(start, end));
    }
    else {
      const newItemsToDisplay = [...itemsToDisplay, ...filteredItems.slice(start, end)];
      setItemsToDisplay(newItemsToDisplay);
    }

  };

  // Event handler for infinite scroll
  const handleInfiniteScroll = (e: { target: { complete: () => void; }; }) => {
    // console.log("handleInfiniteScroll----------------------");
    // Load more items if there are any
    if (searchQuery === '') {

      var filteredItems = items!;

    }
    else {

      if (filterText === 'desc') {

        var filteredItems = items!.filter(item => item.desc.toLowerCase().includes(searchQuery.toLowerCase()));
      }
      else {
        var filteredItems = items!.filter(item => item.text.toLowerCase().includes(searchQuery.toLowerCase()));
      }

    }
    if (itemsToDisplay.length < filteredItems!.length) {
      // loadItemsForPage(currentPage + 1); //EROARE AICI: daca dau setState la asta o sa dea trigger la useEffect iar, care incarca chestii in plus; bine de stiut
      setCurrentPage(currentPage + 1);
      e.target.complete();
    } else {
      e.target.complete(); // Finish infinite scroll if all items have been loaded
    }
  };

  const handleSearch = (query: string) => {
    setSearchQuery(query);
    setItemsToDisplay([]);
    setCurrentPage(1);
    // Reset pagination when searching
    // if (searchQuery === '') {
    //   setFilteredItems(items!);
    // }
    // else {
    //   setFilteredItems(items!.filter(item => item.text.toLowerCase().includes(searchQuery.toLowerCase())));
    // }
    // setItemsToDisplay([]);
    // setCurrentPage(1);

  };

  log('render', fetching);
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          {/* create wrapper component */}
          <IonCol size='4'>
            <IonTitle ref={cardEl} onPointerMove={() => console.log("Hovering")}>Item List</IonTitle>
            {isAuthenticated && <IonButton size='small' onClick={logout}>Logout</IonButton>}
          </IonCol>

        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonLoading isOpen={fetching} message="Fetching items" />
        <IonRow>
          <IonSearchbar
            debounce={1000}
            value={searchQuery}
            onIonChange={e => handleSearch(e.detail.value!)}
          />
          <IonSelect value={filterText} placeholder="Select Filter" onIonChange={e => setFilterText(e.detail.value)}>
            <IonSelectOption value='text'>Text</IonSelectOption>
            <IonSelectOption value='desc'>Desc</IonSelectOption>
          </IonSelect>
        </IonRow>
        {items && (
          <IonList>
            {itemsToDisplay.map(({ _id, text, desc, photo }) => (
              <Item key={_id} _id={_id} text={text} desc={desc} photo={photo} onEdit={id => history.push(`/item/${id}`)} />
            ))}
          </IonList>
        )}
        {fetchingError && (
          <div>{fetchingError.message || 'Failed to fetch items'}</div>
        )}
        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton ref={buttonEl} onPointerEnter={() => { buttonAnimation.current?.play() }} onPointerLeave={() => { buttonAnimation.current?.stop() }} onClick={() => history.push('/item')}>
            <IonIcon icon={add} style={{ color: 'black' }} />
          </IonFabButton>
        </IonFab>
        <IonInfiniteScroll
          threshold="50px"
          onIonInfinite={handleInfiniteScroll}
        >
          <IonInfiniteScrollContent loadingText="Loading more items..."></IonInfiniteScrollContent>
        </IonInfiniteScroll>
      </IonContent>
    </IonPage>
  );
};

export default ItemList;
