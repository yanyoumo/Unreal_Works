;谁给精简下…………
mov SP,#0x20;

mov R4,#83
mov R5,#237
mov R6,#195
;load data
;max b^2 2bytes(2 registers)
;max 4ac 2.5bytes(3 registers)
call hasreal;
jnc fisrthasnotreal
inc R0;
fisrthasnotreal:

mov R4,#232
mov R5,#158
mov R6,#3
;load data again
call hasreal;
jnc secondhasnotreal
inc R1;
secondhasnotreal:

sjmp true_end;

hasreal:
	push 0xE0;save A
	push 0xF0;save B
	push 0x00;save R0
	push 0x01;save R1
	push 0x02;save R2
	push 0x03;save R3
	push 0x07;save R7
	push 0x08;save "R8"
	push 0x09;save "R9"
	push 0x0A;save "R10"
	mov A,R5;
	mov B,R5;
	mul AB;	
	mov R0,A;
	mov R1,B;R1&R0=b^2
	mov A,R4;
	mov B,R6;
	mul AB;
	mov R3,A;moves lower nibble of 4ac to R3
	mov A,#4;
	mul AB;
	mov 0x09,A;
	mov 0x0A,B;R10&R9=higher 1.5 bytes of 4ac
	mov B,#4;
	mov A,R3;
	mul AB;
	mov R7,A;
	mov 0x08,B;R8&R7=lower 2 bytes of 4ac (R9 should be overlapping with R8)
	mov A,0x08;
	mov B,0x09;
	add a,b;
	mov 0x08,a;
	JNC nadd;
	inc 0x0A;"re-arranging" 4ac's place in RAM
	nadd: mov 0x09,0x0A;R9&R8&R7=4ac
	CLR C;
	mov A,0x09
	CJNE A,#0,nend;
	mov A,0x01 
	CJNE A,0x08,next1;
   	mov A,0x00 
	CJNE A,0x07,next1;
	next1:jc nend;
	jnc cend;
   	cend:setb c;
   	Sjmp end;
	nend:clr c
    end:pop 0x0A;re-load "R10"
	pop 0x09;re-load "R9"
	pop 0x08;re-load "R8"
	pop 0x07;re-load R7
	pop 0x03;re-load R3
	pop 0x02;re-load R2
	pop 0x01;re-load R1
	pop 0x00;re-load R0
	pop 0xF0;re-load B
	pop 0xE0;re-load A
	ret;
true_end:
	
	
	
	
	
	
	
	
	
	
	