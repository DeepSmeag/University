import { useEffect } from 'react';
import { Preferences } from '@capacitor/preferences';

export const setPreferences = async (prefKey: string, username: string, password: string, token: string) => {
  await Preferences.set({
    key: prefKey,
    value: JSON.stringify({
      username: username, password: password, token: token,
    })
  });
};
export const writeQueueToPreferences = async (queue: any[]) => {
  await Preferences.set({
    key: 'queue',
    value: JSON.stringify(queue)
  });
}
export const getPreferences = async (pref: string) => {
  const res = await Preferences.get({ key: pref });
  if (res.value) {
    console.log('Found for', pref, ':', JSON.parse(res.value));
  } else {
    console.log('Not found');
  }
  return res.value;
};
export const showPreferences = async () => {
  const { keys } = await Preferences.keys();
  console.log('Keys found', keys);
}
export const removePreferences = async (pref: string) => {
  await Preferences.remove({ key: pref });
  console.log('Keys found after remove', await Preferences.keys());
}
export const clearPreferences = async () => {
  await Preferences.clear();
}

export const usePreferences = () => {
  useEffect(() => {
    // runPreferencesDemo();
  }, []);

  // function runPreferencesDemo() {
  //   (async () => {
  //     // Saving ({ key: string, value: string }) => Promise<void>
  //     await Preferences.set({
  //       key: 'user',
  //       value: JSON.stringify({
  //         username: 'a', password: 'a',
  //       })
  //     });

  //     // Loading value by key ({ key: string }) => Promise<{ value: string | null }>


  //     // Loading keys () => Promise<{ keys: string[] }>
  //     const { keys } = await Preferences.keys();
  //     console.log('Keys found', keys);

  //     // Removing value by key, ({ key: string }) => Promise<void>
  //     await Preferences.remove({ key: 'user' });
  //     console.log('Keys found after remove', await Preferences.keys());

  //     // Clear storage () => Promise<void>
  //     await Preferences.clear();
  //   })();
  // }
};
