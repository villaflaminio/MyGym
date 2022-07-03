# Routing

Il **Routing** è ciò che ci permette di navigare tra le vare pagine della nostra applicazione, in Angular per definire una route è necessario implementare un Array di Oggetti che contengono 2 proprietà: **path** e **component**.

- **Path**: è l’URL della route da raggiungere
- **Component:** sarà il componente angular che verrà mostrato quando si raggiungerà il path

```tsx
//tutte le route create nel progetto
const routes: Routes = [
  { path: Path.Login, component: LoginComponent },
  { path: Path.Registration, component: RegistrationComponent },
  { path: Path.Home, component: HomePageComponent },
  { path: Path.ChiSiamo, component: AboutUsComponent },
  { path: Path.Contatti, component: ContactsComponent },
  { path: Path.GymPage, component: GymComponent,canActivate:[AuthGuard],  data: { roles: [Role.Admin] }},
  { path: Path.GymDetails, component: GymDetailsComponent,canActivate:[AuthGuard],  data: { roles: [Role.Admin] }},
  { path: Path.UserPage, component: UserHomeComponent,canActivate:[AuthGuard] ,  data: { roles: [Role.User]}},
  { path: Path.UsersGymPage, component: UsersGymPageComponent,canActivate:[AuthGuard] ,  data: { roles: [Role.Admin]}},
  { path: Path.SchedePage, component: SchedePageComponent,canActivate:[AuthGuard],  data: { roles: [Role.Admin] }},
  { path: Path.SchedaDetails, component: SchedaDetailComponent,canActivate:[AuthGuard] ,  data: { roles: [Role.Admin,Role.User]}},
  { path: Path.MembershipsPage, component: MembershipPageComponent,canActivate:[AuthGuard],  data: { roles: [Role.Admin] }},
  { path: '**', component: HomePageComponent }
];
```

Per attivare il routing all’interno dell’app basta aggiungere il seguente tag:

```tsx
<router-outlet></router-outlet>
```