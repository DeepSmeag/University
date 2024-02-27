import React, { useEffect, useState } from 'react';
import { Network, ConnectionStatus } from '@capacitor/network';
import { PluginListenerHandle } from '@capacitor/core';
import { triggerSync } from './syncApp';
const initialState = {
  connected: false,
  connectionType: 'unknown',
}
const NetworkState: React.FC = () => {
  const { networkStatus } = useNetwork();
  return (
    <div>
      {/* <p>Network status: {networkStatus.connected ? 'connected' : 'disconnected'}</p>
      <p>Connection type: {networkStatus.connectionType}</p> */}
      {/* aligned to right: green circle if connected, red circle if not */}
      <div style={{ position: 'absolute', top: '20px', right: '20px', width: '20px', height: '20px', borderRadius: '50%', backgroundColor: networkStatus.connected ? 'green' : 'red', zIndex: 999 }}></div>
    </div>
  );
}
export const useNetwork = () => {
  const [networkStatus, setNetworkStatus] = useState(initialState)
  useEffect(() => {
    console.log("useNetwork - useEffect");
    let handler: PluginListenerHandle;
    registerNetworkStatusChange();
    Network.getStatus().then(handleNetworkStatusChange);
    let canceled = false;
    return () => {
      canceled = true;
      handler?.remove();
    }

    async function registerNetworkStatusChange() {
      handler = await Network.addListener('networkStatusChange', handleNetworkStatusChange);
    }

    async function handleNetworkStatusChange(status: ConnectionStatus) {
      console.log('useNetwork - status change', status);
      if (!canceled) {
        if (status.connected) {
          triggerSync();
        }
        setNetworkStatus(status);
      }
    }
  }, [])
  return { networkStatus };
};
export const getNetworkStatus = async () => {
  const status = await Network.getStatus();
  return status;
}

export default NetworkState