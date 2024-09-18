# **Guide to Configuring Horizontal Pod Autoscaler (HPA) in Kubernetes**

The **Horizontal Pod Autoscaler (HPA)** automatically adjusts the number of pod replicas in a Kubernetes deployment based on observed CPU utilization or other select metrics. This guide will walk you through the steps required to configure HPA using the **Metrics Server** for resource monitoring and demonstrate it with a load test using `k6`.

## **Prerequisites**
- Kubernetes cluster setup
- `kubectl` installed and configured
- `k6` installed for load testing

---

## **1. Install the Metrics Server**

The **Metrics Server** is responsible for collecting resource usage data from the nodes and exposing it to the Kubernetes API, which the HPA uses to make scaling decisions.

### **Steps:**
1. Apply the official Metrics Server YAML file:
   ```
   kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
   ```

2. Verify that the Metrics Server is deployed:
   ```
   kubectl get deployment metrics-server -n kube-system
   ```

3. Check the API services to ensure that the Metrics API is registered:
   ```
   kubectl get apiservices
   ```

4. Verify that the Metrics Server pod is running:
   ```
   kubectl get pods -n kube-system
   ```

5. Check the logs of the Metrics Server pod to ensure it is functioning correctly:
   ```
   kubectl logs <metrics-server-pod-name> -n kube-system
   ```

   Replace `<metrics-server-pod-name>` with the actual pod name obtained from the previous command.

---

## **2. Modify the Metrics Server for Internal Communication**

By default, the Metrics Server may have trouble connecting to nodes due to certificate validation issues. To resolve this, we need to modify the Metrics Server deployment to ignore insecure TLS warnings and prefer internal IPs.

### **Steps:**
1. Edit the Metrics Server deployment:
   ```
   kubectl edit deployment metrics-server -n kube-system
   ```

2. Under the `spec.containers.args` section, add the following flags:
   ```yaml
   - --kubelet-insecure-tls
   - --kubelet-preferred-address-types=InternalIP
   ```

3. Save and exit the editor. This configuration will instruct the Metrics Server to ignore insecure TLS errors and prefer internal IP addresses when connecting to Kubelet on each node.

---

## **3. Deploy the Application with HPA Configuration**

Now that the Metrics Server is set up, we can apply our application with the HPA configuration.

### **Steps:**
1. Apply the **HPA** configuration for the `person-api` using the `person-api-horizontal-pod-autoscaler.yaml` file:
   ```
   kubectl apply -f person-api-horizontal-pod-autoscaler.yaml
   ```

2. Verify that the HPA is created:
   ```
   kubectl get hpa
   ```

The HPA will start monitoring the CPU usage of the `person-api` and automatically scale up or down the number of pods based on the thresholds defined in the YAML file.

---

## **4. Simulate Load to Trigger Autoscaling**

To test the autoscaling capabilities of your setup, we will simulate load using the `k6` tool.

### **Steps:**
1. Navigate to the load testing folder where the `load_test_dev.js` and `load_test_test.js` script are located (load_test_dev.js for localhost:18080 and load_test_test.js for localhost:20000):
   ```
   cd load-testing
   ```

2. Run the load test using `k6`:
   ```
   k6 run load_test_dev.js
   ```

This will simulate requests to your `person-api`, increasing its CPU usage.

---

## **5. Monitor HPA in Real Time**

As the load test runs, you can monitor the Horizontal Pod Autoscaler in real time to see how it responds to the increased load.

### **Steps:**
1. Use the following command to continuously watch the scaling events:
   ```
   kubectl get hpa --watch
   ```

This will show the current CPU utilization, the target CPU threshold, and the number of replicas being adjusted in real time. As the CPU usage increases, the HPA will scale up the number of pods, and once the load decreases, it will scale them back down.

---

## **Summary of Key Commands**

1. **Install the Metrics Server**:
   ```
   kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
   ```

2. **Check Metrics Server Status**:
   ```
   kubectl get deployment metrics-server -n kube-system
   kubectl get apiservices
   kubectl get pods -n kube-system
   kubectl logs <metrics-server-pod-name> -n kube-system
   ```

3. **Edit Metrics Server Deployment**:
   ```
   kubectl edit deployment metrics-server -n kube-system
   ```

4. **Deploy HPA Configuration**:
   ```
   kubectl apply -f person-api-horizontal-pod-autoscaler.yaml
   ```

5. **Run Load Test**:
   ```
   k6 run load_test_dev.js
   ```

6. **Watch HPA Scaling**:
   ```
   kubectl get hpa --watch
   ```

---

## **Troubleshooting Tips**

- If you don’t see the HPA scaling as expected, ensure the Metrics Server is properly installed and running. Check the logs for any TLS or communication errors between the server and the nodes.
- You can also manually check the CPU usage of your pods using the following command to verify if the metrics are being collected:
  ```
  kubectl top pods
  ```