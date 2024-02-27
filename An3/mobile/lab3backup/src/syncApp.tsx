import { useContext, useEffect, useState } from 'react';
import { getPreferences, removePreferences } from './usePreferences';
import { updateItem, createItem } from './todo/itemApi';

import { ItemProps } from './todo/ItemProps';

export const triggerSync = () => {



    getPreferences('user').then((prefs) => {
        if (prefs) {
            const { username: u, password: p, token: t } = JSON.parse(prefs);
            console.log('trySync', u, p, t);
            getPreferences('queue').then((callQueue) => {
                console.log('queue', callQueue);
                if (callQueue) {
                    const queue = JSON.parse(callQueue);
                    queue.forEach((item: ItemProps) => {
                        if (item._id) {
                            updateItem(t, item);
                        }
                        else {
                            createItem(t, item);
                        }
                    });
                    removePreferences('queue');
                }
            });
        }
    });


}
// const { items, saving, savingError, saveItem } = useContext(ItemContext);
// export const triggerSync = async () => {
//     const prefs = await getPreferences('user');
//     if (prefs) {
//         const { username: u, password: p, token: t } = JSON.parse(prefs);
//         console.log('trySync', u, p, t);

//         getPreferences('queue').then((callQueue) => {
//             console.log('queue', callQueue);
//             if (callQueue) {
//                 const queue = JSON.parse(callQueue);
//                 queue.forEach((item: ItemProps) => {
//                     saveItem && saveItem(item);
//                 });
//                 removePreferences('callQueue');
//             }
//         });



//     }
// };



