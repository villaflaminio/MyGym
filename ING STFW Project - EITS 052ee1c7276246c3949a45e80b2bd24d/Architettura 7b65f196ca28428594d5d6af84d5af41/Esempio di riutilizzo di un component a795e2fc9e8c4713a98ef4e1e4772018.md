# Esempio di riutilizzo di un component

Ecco un esempio di riutilizzo di un component nel mio progetto.

Il component che utilizzo per **l’inserimento** di un Esercizio nel Database è “esercizio-create.component”, questo con alcuni accorgimenti l’ho predisposto affinché supporti anche la **modifica** di un esercizio.
Ciò che permette di effettuare quest’operazione è la gestione dei Form di Angular, poiché proprio grazie a questo riesco ad effettuare un ***binding*** differente a seconda dell’utilizzo del component.

**esercizio-create.component**

**esercizio-create.component.html**

```html

<div class="row">
    <div *ngIf="data else createEse"> 
        <h3 class="mb-2">Modifica Esercizio</h3> 
    </div>
    <ng-template #createEse>
        <h3 class="mb-2">Aggiungi Esercizio</h3>
    </ng-template>
    <form [formGroup]="createEsForm" (ngSubmit)="onSubmit()" class="mt-3">
        <div class="row justify-content-center pt-1">
                <div class="col-xxl-10 col-xl-12 col-lg-11 col-md-10 col-sm-11 col-12" align="center">
                    <p>
                        <mat-form-field appearance="outline" class="full_width-input">
                            <mat-label>Nome</mat-label>
                            <input matInput placeholder="Nome" formControlName="nome" />
                        </mat-form-field>
                    </p>
                </div>
    
        </div>
    
        <div class="row justify-content-center pt-1">
            <div class="col-xxl-10 col-xl-12 col-lg-11 col-md-10 col-sm-11 col-12 m-0" align="center">
                <p>
                    <mat-form-field appearance="outline" class="full_width-input">
                        <mat-label>Foto</mat-label>
                        <input matInput placeholder="Foto esercizio link dal web" formControlName="foto" />
                    </mat-form-field>
                </p>
            </div>
        </div>
        <div class="row justify-content-center">
                <div class="col-xxl-10 col-xl-12 col-lg-11 col-md-10 col-sm-11 col-12" align="center">
                    <p>
                        <mat-form-field appearance="outline" class="full_width-input">
                            <mat-label>Tempo di recupero</mat-label>
                            <input matInput type="number" placeholder="Tempo di recupero esercizio" formControlName="tempoRecupero" />
                        </mat-form-field>
                    </p>
                </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-xxl-10 col-xl-12 col-lg-11 col-md-10 col-sm-11 col-12" align="center" >
                <p>
                    <mat-form-field appearance="outline" class="full_width-input">
                        <mat-label>Numero ripetizioni</mat-label>
                        <input type="number" matInput placeholder="Numero di ripetizioni" formControlName="numeroRipetizioni" />
                    </mat-form-field>
                </p>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-xxl-10 col-xl-12 col-lg-11 col-md-10 col-sm-11 col-12" align="center" >
                <p>
                    <mat-form-field appearance="outline" class="full_width-input">
                        <mat-label>Tipo</mat-label>
                        <input matInput placeholder="Cardio/Addominali/Gambe/Spalle ecc" formControlName="tipo" />
                    </mat-form-field>
                </p>
            </div>
        </div>
      
    
    
        <div class="row justify-content-center pt-1">
            <div class="col" align="center">
                <button mat-button color="warn" mat-dialog-close>Chiudi</button>
                <button type="submit" [disabled]="createEsForm.invalid" mat-raised-button mat-dialog-close>
                            <span > Salva </span>
                          </button>
            </div>
        </div>
    </form>
</div>
```

**esercizio-create.component.ts**

```tsx
export class EsercizioCreateComponent implements OnInit {

  createEsForm:FormGroup
  modifiedEsercizio:Esercizio
  constructor(
    @Inject(MAT_DIALOG_DATA) public data:Esercizio ,//ricevo i dati dalla pagina precedente
    private esercizioService:EsercizioService,
    private router:Router

  ) { }

  ngOnInit(): void {
    this.modifiedEsercizio= this.data;  
    this.initForm()                           //inizializzo il form
  }
  initForm(){
    this.createEsForm = new FormGroup({
      nome : new FormControl(this.data?this.modifiedEsercizio?.nome:'',Validators.required),
      foto : new FormControl(this.data?this.modifiedEsercizio?.foto:'',Validators.required),
      tempoRecupero : new FormControl(this.data?this.modifiedEsercizio?.tempoRecupero:'',Validators.required),
      numeroRipetizioni : new FormControl(this.data?this.modifiedEsercizio?.numeroRipetizioni:'',Validators.required),
      tipo : new FormControl(this.data?this.modifiedEsercizio?.tipo:'',Validators.required),

    })
  }
  onSubmit(){
    if(!this.data){                                     //se è presente data si tratta di un inserimento
      this.esercizioService.createEsercizio(this.createEsForm.value)
      .subscribe(
        response => {
          this.router.navigateByUrl(Path.SchedePage)
        }
      )
    }else{                                      //altrimenti si tratta di una modifica
      this.esercizioService.updateEsercizio(this.modifiedEsercizio.id,this.createEsForm.value).subscribe()
    }
  }
}
```