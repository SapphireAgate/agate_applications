agate_applications
==================


There are currently three users in the system (u1, u2 and u3) with the associated
passwords (p1, p2 and p3). The simple-client-server application tests if the policy
enforcement mechanism is effective. The server sets a policy on a String and sends
it on the network to a connected client if the client is allowed to receive the 
data, according to the policy. There are currently 6 hard-coded policies the server
can choose from:

```
UserFlowPolicy.POLICY_1 = only u1 can receive the data
UserFlowPolicy.POLICY_2 = only u2 can receive the data
UserFlowPolicy.POLICY_3 = only u3 can receive the data
UserFlowPolicy.POLICY_4 = only u1 and u2 can receive the data
UserFlowPolicy.POLICY_5 = only u1 and u3 can receive the data
UserFlowPolicy.POLICY_6 = only u2 and u3 can receive the data
```

Depending on what credentials the client uses to identify to the runtime
and what the policy the server has set on the String, the client receives the
data or not.
