	.cpu cortex-m4
	.arch armv7e-m
	.fpu fpv4-sp-d16
	.eabi_attribute 27, 1
	.eabi_attribute 28, 1
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 1
	.eabi_attribute 30, 6
	.eabi_attribute 34, 1
	.eabi_attribute 38, 1
	.eabi_attribute 18, 4
	.file	"bsp.c"
	.text
.Ltext0:
	.section	.text.bsp_button_is_pressed,"ax",%progbits
	.align	1
	.global	bsp_button_is_pressed
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_button_is_pressed, %function
bsp_button_is_pressed:
.LFB165:
	.file 1 "/home/italy/Git Repos and Program Testing/dwm1001-examples-master/nRF5_SDK_14.2.0/components/libraries/bsp/bsp.c"
	.loc 1 113 1
	@ args = 0, pretend = 0, frame = 8
	@ frame_needed = 0, uses_anonymous_args = 0
	push	{lr}
.LCFI0:
	sub	sp, sp, #12
.LCFI1:
	str	r0, [sp, #4]
	.loc 1 114 12
	ldr	r0, [sp, #4]
	bl	bsp_board_button_state_get
	mov	r3, r0
	.loc 1 115 1
	mov	r0, r3
	add	sp, sp, #12
.LCFI2:
	@ sp needed
	ldr	pc, [sp], #4
.LFE165:
	.size	bsp_button_is_pressed, .-bsp_button_is_pressed
	.section	.text.bsp_indication_set,"ax",%progbits
	.align	1
	.global	bsp_indication_set
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_indication_set, %function
bsp_indication_set:
.LFB166:
	.loc 1 457 1
	@ args = 0, pretend = 0, frame = 16
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	sub	sp, sp, #16
.LCFI3:
	mov	r3, r0
	strb	r3, [sp, #7]
	.loc 1 458 14
	movs	r3, #0
	str	r3, [sp, #12]
	.loc 1 468 12
	ldr	r3, [sp, #12]
	.loc 1 469 1
	mov	r0, r3
	add	sp, sp, #16
.LCFI4:
	@ sp needed
	bx	lr
.LFE166:
	.size	bsp_indication_set, .-bsp_indication_set
	.section	.text.bsp_init,"ax",%progbits
	.align	1
	.global	bsp_init
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_init, %function
bsp_init:
.LFB167:
	.loc 1 473 1
	@ args = 0, pretend = 0, frame = 16
	@ frame_needed = 0, uses_anonymous_args = 0
	push	{lr}
.LCFI5:
	sub	sp, sp, #20
.LCFI6:
	str	r0, [sp, #4]
	str	r1, [sp]
	.loc 1 474 14
	movs	r3, #0
	str	r3, [sp, #12]
	.loc 1 514 14
	ldr	r3, [sp, #4]
	and	r3, r3, #2
	.loc 1 514 8
	cmp	r3, #0
	beq	.L6
	.loc 1 516 9
	bl	bsp_board_buttons_init
.L6:
	.loc 1 541 12
	ldr	r3, [sp, #12]
	.loc 1 542 1
	mov	r0, r3
	add	sp, sp, #20
.LCFI7:
	@ sp needed
	ldr	pc, [sp], #4
.LFE167:
	.size	bsp_init, .-bsp_init
	.section	.text.bsp_buttons_enable,"ax",%progbits
	.align	1
	.global	bsp_buttons_enable
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_buttons_enable, %function
bsp_buttons_enable:
.LFB168:
	.loc 1 591 1
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	.loc 1 595 12
	movs	r3, #6
	.loc 1 597 1
	mov	r0, r3
	bx	lr
.LFE168:
	.size	bsp_buttons_enable, .-bsp_buttons_enable
	.section	.text.bsp_buttons_disable,"ax",%progbits
	.align	1
	.global	bsp_buttons_disable
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_buttons_disable, %function
bsp_buttons_disable:
.LFB169:
	.loc 1 600 1
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	.loc 1 604 12
	movs	r3, #6
	.loc 1 606 1
	mov	r0, r3
	bx	lr
.LFE169:
	.size	bsp_buttons_disable, .-bsp_buttons_disable
	.section	.text.bsp_wakeup_button_enable,"ax",%progbits
	.align	1
	.global	bsp_wakeup_button_enable
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_wakeup_button_enable, %function
bsp_wakeup_button_enable:
.LFB170:
	.loc 1 609 1
	@ args = 0, pretend = 0, frame = 8
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	sub	sp, sp, #8
.LCFI8:
	str	r0, [sp, #4]
	.loc 1 616 12
	movs	r3, #6
	.loc 1 618 1
	mov	r0, r3
	add	sp, sp, #8
.LCFI9:
	@ sp needed
	bx	lr
.LFE170:
	.size	bsp_wakeup_button_enable, .-bsp_wakeup_button_enable
	.section	.text.bsp_wakeup_button_disable,"ax",%progbits
	.align	1
	.global	bsp_wakeup_button_disable
	.syntax unified
	.thumb
	.thumb_func
	.type	bsp_wakeup_button_disable, %function
bsp_wakeup_button_disable:
.LFB171:
	.loc 1 621 1
	@ args = 0, pretend = 0, frame = 8
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	sub	sp, sp, #8
.LCFI10:
	str	r0, [sp, #4]
	.loc 1 628 12
	movs	r3, #6
	.loc 1 630 1
	mov	r0, r3
	add	sp, sp, #8
.LCFI11:
	@ sp needed
	bx	lr
.LFE171:
	.size	bsp_wakeup_button_disable, .-bsp_wakeup_button_disable
	.section	.debug_frame,"",%progbits
.Lframe0:
	.4byte	.LECIE0-.LSCIE0
.LSCIE0:
	.4byte	0xffffffff
	.byte	0x3
	.ascii	"\000"
	.uleb128 0x1
	.sleb128 -4
	.uleb128 0xe
	.byte	0xc
	.uleb128 0xd
	.uleb128 0
	.align	2
.LECIE0:
.LSFDE0:
	.4byte	.LEFDE0-.LASFDE0
.LASFDE0:
	.4byte	.Lframe0
	.4byte	.LFB165
	.4byte	.LFE165-.LFB165
	.byte	0x4
	.4byte	.LCFI0-.LFB165
	.byte	0xe
	.uleb128 0x4
	.byte	0x8e
	.uleb128 0x1
	.byte	0x4
	.4byte	.LCFI1-.LCFI0
	.byte	0xe
	.uleb128 0x10
	.byte	0x4
	.4byte	.LCFI2-.LCFI1
	.byte	0xe
	.uleb128 0x4
	.align	2
.LEFDE0:
.LSFDE2:
	.4byte	.LEFDE2-.LASFDE2
.LASFDE2:
	.4byte	.Lframe0
	.4byte	.LFB166
	.4byte	.LFE166-.LFB166
	.byte	0x4
	.4byte	.LCFI3-.LFB166
	.byte	0xe
	.uleb128 0x10
	.byte	0x4
	.4byte	.LCFI4-.LCFI3
	.byte	0xe
	.uleb128 0
	.align	2
.LEFDE2:
.LSFDE4:
	.4byte	.LEFDE4-.LASFDE4
.LASFDE4:
	.4byte	.Lframe0
	.4byte	.LFB167
	.4byte	.LFE167-.LFB167
	.byte	0x4
	.4byte	.LCFI5-.LFB167
	.byte	0xe
	.uleb128 0x4
	.byte	0x8e
	.uleb128 0x1
	.byte	0x4
	.4byte	.LCFI6-.LCFI5
	.byte	0xe
	.uleb128 0x18
	.byte	0x4
	.4byte	.LCFI7-.LCFI6
	.byte	0xe
	.uleb128 0x4
	.align	2
.LEFDE4:
.LSFDE6:
	.4byte	.LEFDE6-.LASFDE6
.LASFDE6:
	.4byte	.Lframe0
	.4byte	.LFB168
	.4byte	.LFE168-.LFB168
	.align	2
.LEFDE6:
.LSFDE8:
	.4byte	.LEFDE8-.LASFDE8
.LASFDE8:
	.4byte	.Lframe0
	.4byte	.LFB169
	.4byte	.LFE169-.LFB169
	.align	2
.LEFDE8:
.LSFDE10:
	.4byte	.LEFDE10-.LASFDE10
.LASFDE10:
	.4byte	.Lframe0
	.4byte	.LFB170
	.4byte	.LFE170-.LFB170
	.byte	0x4
	.4byte	.LCFI8-.LFB170
	.byte	0xe
	.uleb128 0x8
	.byte	0x4
	.4byte	.LCFI9-.LCFI8
	.byte	0xe
	.uleb128 0
	.align	2
.LEFDE10:
.LSFDE12:
	.4byte	.LEFDE12-.LASFDE12
.LASFDE12:
	.4byte	.Lframe0
	.4byte	.LFB171
	.4byte	.LFE171-.LFB171
	.byte	0x4
	.4byte	.LCFI10-.LFB171
	.byte	0xe
	.uleb128 0x8
	.byte	0x4
	.4byte	.LCFI11-.LCFI10
	.byte	0xe
	.uleb128 0
	.align	2
.LEFDE12:
	.text
.Letext0:
	.file 2 "/usr/share/segger_embedded_studio_for_arm_6.20a/include/stdint.h"
	.file 3 "/home/italy/Git Repos and Program Testing/dwm1001-examples-master/nRF5_SDK_14.2.0/components/libraries/bsp/bsp.h"
	.section	.debug_info,"",%progbits
.Ldebug_info0:
	.4byte	0x32c
	.2byte	0x4
	.4byte	.Ldebug_abbrev0
	.byte	0x4
	.uleb128 0x1
	.4byte	.LASF76
	.byte	0xc
	.4byte	.LASF77
	.4byte	.LASF78
	.4byte	.Ldebug_ranges0+0
	.4byte	0
	.4byte	.Ldebug_line0
	.uleb128 0x2
	.byte	0x1
	.byte	0x8
	.4byte	.LASF0
	.uleb128 0x2
	.byte	0x4
	.byte	0x7
	.4byte	.LASF1
	.uleb128 0x2
	.byte	0x1
	.byte	0x6
	.4byte	.LASF2
	.uleb128 0x2
	.byte	0x1
	.byte	0x8
	.4byte	.LASF3
	.uleb128 0x2
	.byte	0x2
	.byte	0x5
	.4byte	.LASF4
	.uleb128 0x2
	.byte	0x2
	.byte	0x7
	.4byte	.LASF5
	.uleb128 0x3
	.byte	0x4
	.byte	0x5
	.ascii	"int\000"
	.uleb128 0x4
	.4byte	.LASF35
	.byte	0x2
	.byte	0xdb
	.byte	0x1c
	.4byte	0x2c
	.uleb128 0x2
	.byte	0x8
	.byte	0x5
	.4byte	.LASF6
	.uleb128 0x2
	.byte	0x8
	.byte	0x7
	.4byte	.LASF7
	.uleb128 0x2
	.byte	0x4
	.byte	0x5
	.4byte	.LASF8
	.uleb128 0x5
	.byte	0x7
	.byte	0x1
	.4byte	0x3a
	.byte	0x3
	.byte	0x74
	.byte	0x1
	.4byte	0x122
	.uleb128 0x6
	.4byte	.LASF9
	.byte	0
	.uleb128 0x6
	.4byte	.LASF10
	.byte	0
	.uleb128 0x6
	.4byte	.LASF11
	.byte	0x1
	.uleb128 0x6
	.4byte	.LASF12
	.byte	0x2
	.uleb128 0x6
	.4byte	.LASF13
	.byte	0x3
	.uleb128 0x6
	.4byte	.LASF14
	.byte	0x4
	.uleb128 0x6
	.4byte	.LASF15
	.byte	0x5
	.uleb128 0x6
	.4byte	.LASF16
	.byte	0x6
	.uleb128 0x6
	.4byte	.LASF17
	.byte	0x7
	.uleb128 0x6
	.4byte	.LASF18
	.byte	0x8
	.uleb128 0x6
	.4byte	.LASF19
	.byte	0x9
	.uleb128 0x6
	.4byte	.LASF20
	.byte	0xa
	.uleb128 0x6
	.4byte	.LASF21
	.byte	0xb
	.uleb128 0x6
	.4byte	.LASF22
	.byte	0xc
	.uleb128 0x6
	.4byte	.LASF23
	.byte	0xd
	.uleb128 0x6
	.4byte	.LASF24
	.byte	0xe
	.uleb128 0x6
	.4byte	.LASF25
	.byte	0xf
	.uleb128 0x6
	.4byte	.LASF26
	.byte	0x10
	.uleb128 0x6
	.4byte	.LASF27
	.byte	0x11
	.uleb128 0x6
	.4byte	.LASF28
	.byte	0x12
	.uleb128 0x6
	.4byte	.LASF29
	.byte	0x13
	.uleb128 0x6
	.4byte	.LASF30
	.byte	0x14
	.uleb128 0x6
	.4byte	.LASF31
	.byte	0x15
	.uleb128 0x6
	.4byte	.LASF32
	.byte	0x16
	.uleb128 0x6
	.4byte	.LASF33
	.byte	0x17
	.uleb128 0x6
	.4byte	.LASF34
	.byte	0x17
	.byte	0
	.uleb128 0x4
	.4byte	.LASF36
	.byte	0x3
	.byte	0x8f
	.byte	0x3
	.4byte	0x77
	.uleb128 0x5
	.byte	0x7
	.byte	0x1
	.4byte	0x3a
	.byte	0x3
	.byte	0x96
	.byte	0x1
	.4byte	0x1c7
	.uleb128 0x6
	.4byte	.LASF37
	.byte	0
	.uleb128 0x6
	.4byte	.LASF38
	.byte	0x1
	.uleb128 0x6
	.4byte	.LASF39
	.byte	0x2
	.uleb128 0x6
	.4byte	.LASF40
	.byte	0x3
	.uleb128 0x6
	.4byte	.LASF41
	.byte	0x4
	.uleb128 0x6
	.4byte	.LASF42
	.byte	0x5
	.uleb128 0x6
	.4byte	.LASF43
	.byte	0x6
	.uleb128 0x6
	.4byte	.LASF44
	.byte	0x7
	.uleb128 0x6
	.4byte	.LASF45
	.byte	0x8
	.uleb128 0x6
	.4byte	.LASF46
	.byte	0x9
	.uleb128 0x6
	.4byte	.LASF47
	.byte	0xa
	.uleb128 0x6
	.4byte	.LASF48
	.byte	0xb
	.uleb128 0x6
	.4byte	.LASF49
	.byte	0xc
	.uleb128 0x6
	.4byte	.LASF50
	.byte	0xd
	.uleb128 0x6
	.4byte	.LASF51
	.byte	0xe
	.uleb128 0x6
	.4byte	.LASF52
	.byte	0xf
	.uleb128 0x6
	.4byte	.LASF53
	.byte	0x10
	.uleb128 0x6
	.4byte	.LASF54
	.byte	0x11
	.uleb128 0x6
	.4byte	.LASF55
	.byte	0x12
	.uleb128 0x6
	.4byte	.LASF56
	.byte	0x13
	.uleb128 0x6
	.4byte	.LASF57
	.byte	0x14
	.uleb128 0x6
	.4byte	.LASF58
	.byte	0x15
	.uleb128 0x6
	.4byte	.LASF59
	.byte	0x15
	.byte	0
	.uleb128 0x4
	.4byte	.LASF60
	.byte	0x3
	.byte	0xae
	.byte	0x3
	.4byte	0x12e
	.uleb128 0x4
	.4byte	.LASF61
	.byte	0x3
	.byte	0xbf
	.byte	0x11
	.4byte	0x1df
	.uleb128 0x7
	.byte	0x4
	.4byte	0x1e5
	.uleb128 0x8
	.4byte	0x1f0
	.uleb128 0x9
	.4byte	0x1c7
	.byte	0
	.uleb128 0xa
	.4byte	.LASF62
	.byte	0x1
	.2byte	0x26c
	.byte	0xa
	.4byte	0x56
	.4byte	.LFB171
	.4byte	.LFE171-.LFB171
	.uleb128 0x1
	.byte	0x9c
	.4byte	0x21c
	.uleb128 0xb
	.4byte	.LASF64
	.byte	0x1
	.2byte	0x26c
	.byte	0x2d
	.4byte	0x56
	.uleb128 0x2
	.byte	0x91
	.sleb128 -4
	.byte	0
	.uleb128 0xa
	.4byte	.LASF63
	.byte	0x1
	.2byte	0x260
	.byte	0xa
	.4byte	0x56
	.4byte	.LFB170
	.4byte	.LFE170-.LFB170
	.uleb128 0x1
	.byte	0x9c
	.4byte	0x248
	.uleb128 0xb
	.4byte	.LASF64
	.byte	0x1
	.2byte	0x260
	.byte	0x2c
	.4byte	0x56
	.uleb128 0x2
	.byte	0x91
	.sleb128 -4
	.byte	0
	.uleb128 0xc
	.4byte	.LASF65
	.byte	0x1
	.2byte	0x257
	.byte	0xa
	.4byte	0x56
	.4byte	.LFB169
	.4byte	.LFE169-.LFB169
	.uleb128 0x1
	.byte	0x9c
	.uleb128 0xc
	.4byte	.LASF66
	.byte	0x1
	.2byte	0x24e
	.byte	0xa
	.4byte	0x56
	.4byte	.LFB168
	.4byte	.LFE168-.LFB168
	.uleb128 0x1
	.byte	0x9c
	.uleb128 0xd
	.4byte	.LASF67
	.byte	0x1
	.2byte	0x1d8
	.byte	0xa
	.4byte	0x56
	.4byte	.LFB167
	.4byte	.LFE167-.LFB167
	.uleb128 0x1
	.byte	0x9c
	.4byte	0x2c2
	.uleb128 0xb
	.4byte	.LASF68
	.byte	0x1
	.2byte	0x1d8
	.byte	0x1c
	.4byte	0x56
	.uleb128 0x2
	.byte	0x91
	.sleb128 -20
	.uleb128 0xb
	.4byte	.LASF69
	.byte	0x1
	.2byte	0x1d8
	.byte	0x37
	.4byte	0x1d3
	.uleb128 0x2
	.byte	0x91
	.sleb128 -24
	.uleb128 0xe
	.4byte	.LASF72
	.byte	0x1
	.2byte	0x1da
	.byte	0xe
	.4byte	0x56
	.uleb128 0x2
	.byte	0x91
	.sleb128 -12
	.byte	0
	.uleb128 0xa
	.4byte	.LASF70
	.byte	0x1
	.2byte	0x1c8
	.byte	0xa
	.4byte	0x56
	.4byte	.LFB166
	.4byte	.LFE166-.LFB166
	.uleb128 0x1
	.byte	0x9c
	.4byte	0x2fe
	.uleb128 0xb
	.4byte	.LASF71
	.byte	0x1
	.2byte	0x1c8
	.byte	0x2e
	.4byte	0x122
	.uleb128 0x2
	.byte	0x91
	.sleb128 -9
	.uleb128 0xe
	.4byte	.LASF72
	.byte	0x1
	.2byte	0x1ca
	.byte	0xe
	.4byte	0x56
	.uleb128 0x2
	.byte	0x91
	.sleb128 -4
	.byte	0
	.uleb128 0xf
	.4byte	.LASF73
	.byte	0x1
	.byte	0x70
	.byte	0x6
	.4byte	0x328
	.4byte	.LFB165
	.4byte	.LFE165-.LFB165
	.uleb128 0x1
	.byte	0x9c
	.4byte	0x328
	.uleb128 0x10
	.4byte	.LASF74
	.byte	0x1
	.byte	0x70
	.byte	0x25
	.4byte	0x56
	.uleb128 0x2
	.byte	0x91
	.sleb128 -12
	.byte	0
	.uleb128 0x2
	.byte	0x1
	.byte	0x2
	.4byte	.LASF75
	.byte	0
	.section	.debug_abbrev,"",%progbits
.Ldebug_abbrev0:
	.uleb128 0x1
	.uleb128 0x11
	.byte	0x1
	.uleb128 0x25
	.uleb128 0xe
	.uleb128 0x13
	.uleb128 0xb
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x1b
	.uleb128 0xe
	.uleb128 0x2134
	.uleb128 0x19
	.uleb128 0x55
	.uleb128 0x17
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x10
	.uleb128 0x17
	.byte	0
	.byte	0
	.uleb128 0x2
	.uleb128 0x24
	.byte	0
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0x3e
	.uleb128 0xb
	.uleb128 0x3
	.uleb128 0xe
	.byte	0
	.byte	0
	.uleb128 0x3
	.uleb128 0x24
	.byte	0
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0x3e
	.uleb128 0xb
	.uleb128 0x3
	.uleb128 0x8
	.byte	0
	.byte	0
	.uleb128 0x4
	.uleb128 0x16
	.byte	0
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0xb
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0x5
	.uleb128 0x4
	.byte	0x1
	.uleb128 0x3e
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0xb
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x1
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0x6
	.uleb128 0x28
	.byte	0
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x1c
	.uleb128 0xb
	.byte	0
	.byte	0
	.uleb128 0x7
	.uleb128 0xf
	.byte	0
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0x8
	.uleb128 0x15
	.byte	0x1
	.uleb128 0x27
	.uleb128 0x19
	.uleb128 0x1
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0x9
	.uleb128 0x5
	.byte	0
	.uleb128 0x49
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0xa
	.uleb128 0x2e
	.byte	0x1
	.uleb128 0x3f
	.uleb128 0x19
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0x5
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x27
	.uleb128 0x19
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x12
	.uleb128 0x6
	.uleb128 0x40
	.uleb128 0x18
	.uleb128 0x2117
	.uleb128 0x19
	.uleb128 0x1
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0xb
	.uleb128 0x5
	.byte	0
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0x5
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x2
	.uleb128 0x18
	.byte	0
	.byte	0
	.uleb128 0xc
	.uleb128 0x2e
	.byte	0
	.uleb128 0x3f
	.uleb128 0x19
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0x5
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x27
	.uleb128 0x19
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x12
	.uleb128 0x6
	.uleb128 0x40
	.uleb128 0x18
	.uleb128 0x2117
	.uleb128 0x19
	.byte	0
	.byte	0
	.uleb128 0xd
	.uleb128 0x2e
	.byte	0x1
	.uleb128 0x3f
	.uleb128 0x19
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0x5
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x27
	.uleb128 0x19
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x12
	.uleb128 0x6
	.uleb128 0x40
	.uleb128 0x18
	.uleb128 0x2116
	.uleb128 0x19
	.uleb128 0x1
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0xe
	.uleb128 0x34
	.byte	0
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0x5
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x2
	.uleb128 0x18
	.byte	0
	.byte	0
	.uleb128 0xf
	.uleb128 0x2e
	.byte	0x1
	.uleb128 0x3f
	.uleb128 0x19
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0xb
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x27
	.uleb128 0x19
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x12
	.uleb128 0x6
	.uleb128 0x40
	.uleb128 0x18
	.uleb128 0x2116
	.uleb128 0x19
	.uleb128 0x1
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0x10
	.uleb128 0x5
	.byte	0
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0xb
	.uleb128 0x39
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x2
	.uleb128 0x18
	.byte	0
	.byte	0
	.byte	0
	.section	.debug_pubnames,"",%progbits
	.4byte	0xb6
	.2byte	0x2
	.4byte	.Ldebug_info0
	.4byte	0x330
	.4byte	0x1f0
	.ascii	"bsp_wakeup_button_disable\000"
	.4byte	0x21c
	.ascii	"bsp_wakeup_button_enable\000"
	.4byte	0x248
	.ascii	"bsp_buttons_disable\000"
	.4byte	0x25f
	.ascii	"bsp_buttons_enable\000"
	.4byte	0x276
	.ascii	"bsp_init\000"
	.4byte	0x2c2
	.ascii	"bsp_indication_set\000"
	.4byte	0x2fe
	.ascii	"bsp_button_is_pressed\000"
	.4byte	0
	.section	.debug_pubtypes,"",%progbits
	.4byte	0x106
	.2byte	0x2
	.4byte	.Ldebug_info0
	.4byte	0x330
	.4byte	0x25
	.ascii	"char\000"
	.4byte	0x2c
	.ascii	"unsigned int\000"
	.4byte	0x33
	.ascii	"signed char\000"
	.4byte	0x3a
	.ascii	"unsigned char\000"
	.4byte	0x41
	.ascii	"short int\000"
	.4byte	0x48
	.ascii	"short unsigned int\000"
	.4byte	0x4f
	.ascii	"int\000"
	.4byte	0x56
	.ascii	"uint32_t\000"
	.4byte	0x62
	.ascii	"long long int\000"
	.4byte	0x69
	.ascii	"long long unsigned int\000"
	.4byte	0x70
	.ascii	"long int\000"
	.4byte	0x122
	.ascii	"bsp_indication_t\000"
	.4byte	0x1c7
	.ascii	"bsp_event_t\000"
	.4byte	0x1d3
	.ascii	"bsp_event_callback_t\000"
	.4byte	0x328
	.ascii	"_Bool\000"
	.4byte	0
	.section	.debug_aranges,"",%progbits
	.4byte	0x4c
	.2byte	0x2
	.4byte	.Ldebug_info0
	.byte	0x4
	.byte	0
	.2byte	0
	.2byte	0
	.4byte	.LFB165
	.4byte	.LFE165-.LFB165
	.4byte	.LFB166
	.4byte	.LFE166-.LFB166
	.4byte	.LFB167
	.4byte	.LFE167-.LFB167
	.4byte	.LFB168
	.4byte	.LFE168-.LFB168
	.4byte	.LFB169
	.4byte	.LFE169-.LFB169
	.4byte	.LFB170
	.4byte	.LFE170-.LFB170
	.4byte	.LFB171
	.4byte	.LFE171-.LFB171
	.4byte	0
	.4byte	0
	.section	.debug_ranges,"",%progbits
.Ldebug_ranges0:
	.4byte	.LFB165
	.4byte	.LFE165
	.4byte	.LFB166
	.4byte	.LFE166
	.4byte	.LFB167
	.4byte	.LFE167
	.4byte	.LFB168
	.4byte	.LFE168
	.4byte	.LFB169
	.4byte	.LFE169
	.4byte	.LFB170
	.4byte	.LFE170
	.4byte	.LFB171
	.4byte	.LFE171
	.4byte	0
	.4byte	0
	.section	.debug_line,"",%progbits
.Ldebug_line0:
	.section	.debug_str,"MS",%progbits,1
.LASF13:
	.ascii	"BSP_INDICATE_ADVERTISING_WHITELIST\000"
.LASF59:
	.ascii	"BSP_EVENT_KEY_LAST\000"
.LASF64:
	.ascii	"button_idx\000"
.LASF4:
	.ascii	"short int\000"
.LASF42:
	.ascii	"BSP_EVENT_ADVERTISING_START\000"
.LASF28:
	.ascii	"BSP_INDICATE_USER_STATE_OFF\000"
.LASF7:
	.ascii	"long long unsigned int\000"
.LASF23:
	.ascii	"BSP_INDICATE_ALERT_0\000"
.LASF24:
	.ascii	"BSP_INDICATE_ALERT_1\000"
.LASF33:
	.ascii	"BSP_INDICATE_USER_STATE_ON\000"
.LASF25:
	.ascii	"BSP_INDICATE_ALERT_2\000"
.LASF77:
	.ascii	"/home/italy/Git Repos and Program Testing/dwm1001-e"
	.ascii	"xamples-master/nRF5_SDK_14.2.0/components/libraries"
	.ascii	"/bsp/bsp.c\000"
.LASF26:
	.ascii	"BSP_INDICATE_ALERT_3\000"
.LASF61:
	.ascii	"bsp_event_callback_t\000"
.LASF36:
	.ascii	"bsp_indication_t\000"
.LASF63:
	.ascii	"bsp_wakeup_button_enable\000"
.LASF10:
	.ascii	"BSP_INDICATE_IDLE\000"
.LASF35:
	.ascii	"uint32_t\000"
.LASF31:
	.ascii	"BSP_INDICATE_USER_STATE_2\000"
.LASF34:
	.ascii	"BSP_INDICATE_LAST\000"
.LASF48:
	.ascii	"BSP_EVENT_WAKEUP\000"
.LASF37:
	.ascii	"BSP_EVENT_NOTHING\000"
.LASF6:
	.ascii	"long long int\000"
.LASF65:
	.ascii	"bsp_buttons_disable\000"
.LASF60:
	.ascii	"bsp_event_t\000"
.LASF49:
	.ascii	"BSP_EVENT_SYSOFF\000"
.LASF8:
	.ascii	"long int\000"
.LASF38:
	.ascii	"BSP_EVENT_DEFAULT\000"
.LASF73:
	.ascii	"bsp_button_is_pressed\000"
.LASF44:
	.ascii	"BSP_EVENT_WHITELIST_OFF\000"
.LASF74:
	.ascii	"button\000"
.LASF76:
	.ascii	"GNU C99 10.3.1 20210824 (release) -fmessage-length="
	.ascii	"0 -std=gnu99 -mcpu=cortex-m4 -mlittle-endian -mfloa"
	.ascii	"t-abi=hard -mfpu=fpv4-sp-d16 -mthumb -mtp=soft -mfp"
	.ascii	"16-format=ieee -munaligned-access -gdwarf-4 -g2 -gp"
	.ascii	"ubnames -fomit-frame-pointer -fno-dwarf2-cfi-asm -f"
	.ascii	"function-sections -fdata-sections -fshort-enums -fn"
	.ascii	"o-common\000"
.LASF21:
	.ascii	"BSP_INDICATE_RCV_ERROR\000"
.LASF39:
	.ascii	"BSP_EVENT_CLEAR_BONDING_DATA\000"
.LASF20:
	.ascii	"BSP_INDICATE_RCV_OK\000"
.LASF17:
	.ascii	"BSP_INDICATE_CONNECTED\000"
.LASF66:
	.ascii	"bsp_buttons_enable\000"
.LASF62:
	.ascii	"bsp_wakeup_button_disable\000"
.LASF3:
	.ascii	"unsigned char\000"
.LASF78:
	.ascii	"/home/italy/Git Repos and Program Testing/dwm1001-e"
	.ascii	"xamples-master/examples/ss_twr_resp/SES\000"
.LASF22:
	.ascii	"BSP_INDICATE_FATAL_ERROR\000"
.LASF2:
	.ascii	"signed char\000"
.LASF16:
	.ascii	"BSP_INDICATE_BONDING\000"
.LASF29:
	.ascii	"BSP_INDICATE_USER_STATE_0\000"
.LASF30:
	.ascii	"BSP_INDICATE_USER_STATE_1\000"
.LASF68:
	.ascii	"type\000"
.LASF32:
	.ascii	"BSP_INDICATE_USER_STATE_3\000"
.LASF19:
	.ascii	"BSP_INDICATE_SEND_ERROR\000"
.LASF1:
	.ascii	"unsigned int\000"
.LASF12:
	.ascii	"BSP_INDICATE_ADVERTISING\000"
.LASF18:
	.ascii	"BSP_INDICATE_SENT_OK\000"
.LASF5:
	.ascii	"short unsigned int\000"
.LASF0:
	.ascii	"char\000"
.LASF51:
	.ascii	"BSP_EVENT_KEY_0\000"
.LASF52:
	.ascii	"BSP_EVENT_KEY_1\000"
.LASF53:
	.ascii	"BSP_EVENT_KEY_2\000"
.LASF54:
	.ascii	"BSP_EVENT_KEY_3\000"
.LASF55:
	.ascii	"BSP_EVENT_KEY_4\000"
.LASF56:
	.ascii	"BSP_EVENT_KEY_5\000"
.LASF57:
	.ascii	"BSP_EVENT_KEY_6\000"
.LASF58:
	.ascii	"BSP_EVENT_KEY_7\000"
.LASF72:
	.ascii	"err_code\000"
.LASF75:
	.ascii	"_Bool\000"
.LASF15:
	.ascii	"BSP_INDICATE_ADVERTISING_DIRECTED\000"
.LASF50:
	.ascii	"BSP_EVENT_DFU\000"
.LASF70:
	.ascii	"bsp_indication_set\000"
.LASF47:
	.ascii	"BSP_EVENT_SLEEP\000"
.LASF67:
	.ascii	"bsp_init\000"
.LASF40:
	.ascii	"BSP_EVENT_CLEAR_ALERT\000"
.LASF27:
	.ascii	"BSP_INDICATE_ALERT_OFF\000"
.LASF45:
	.ascii	"BSP_EVENT_BOND\000"
.LASF71:
	.ascii	"indicate\000"
.LASF41:
	.ascii	"BSP_EVENT_DISCONNECT\000"
.LASF11:
	.ascii	"BSP_INDICATE_SCANNING\000"
.LASF43:
	.ascii	"BSP_EVENT_ADVERTISING_STOP\000"
.LASF9:
	.ascii	"BSP_INDICATE_FIRST\000"
.LASF14:
	.ascii	"BSP_INDICATE_ADVERTISING_SLOW\000"
.LASF69:
	.ascii	"callback\000"
.LASF46:
	.ascii	"BSP_EVENT_RESET\000"
	.ident	"GCC: (based on arm-10.3-2021.10 GCC) 10.3.1 20210824 (release)"
