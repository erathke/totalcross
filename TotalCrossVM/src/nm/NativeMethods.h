// Copyright (C) 2000-2013 SuperWaba Ltda.
// Copyright (C) 2014-2020 TotalCross Global Mobile Platform Ltda.
//
// SPDX-License-Identifier: LGPL-2.1-only

#ifndef NATIVEMETHODS_H
#define NATIVEMETHODS_H

#ifdef __cplusplus
extern "C" {
#endif

TC_API void jlC_forName_s(NMParams p);
TC_API void jlC_newInstance(NMParams p);
TC_API void jlC_isInstance_o(NMParams p);
TC_API void jlC_isAssignableFrom_c(NMParams p);
TC_API void jlC_isInterface(NMParams p);
TC_API void jlC_isArray(NMParams p);
TC_API void jlC_isPrimitive(NMParams p);
TC_API void jlC_getSuperclass(NMParams p);
TC_API void jlC_getInterfaces(NMParams p);
TC_API void jlC_getComponentType(NMParams p);
TC_API void jlC_getModifiers(NMParams p);
TC_API void jlC_getFields(NMParams p);
TC_API void jlC_getMethods(NMParams p);
TC_API void jlC_getConstructors(NMParams p);
TC_API void jlC_getField_s(NMParams p);
TC_API void jlC_getMethod_sC(NMParams p);
TC_API void jlC_getConstructor_C(NMParams p);
TC_API void jlC_getDeclaredFields(NMParams p);
TC_API void jlC_getDeclaredMethods(NMParams p);
TC_API void jlC_getDeclaredConstructors(NMParams p);
TC_API void jlC_getDeclaredField_s(NMParams p);
TC_API void jlC_getDeclaredMethod_sC(NMParams p);
TC_API void jlC_getDeclaredConstructor_C(NMParams p);
TC_API void jlrA_newInstance_ci(NMParams p);
TC_API void jlrA_newInstance_cI(NMParams p);
TC_API void jlrA_getLength_o(NMParams p);
TC_API void jlrA_get_oi(NMParams p);
TC_API void jlrA_getBoolean_oi(NMParams p);
TC_API void jlrA_getByte_oi(NMParams p);
TC_API void jlrA_getChar_oi(NMParams p);
TC_API void jlrA_getShort_oi(NMParams p);
TC_API void jlrA_getInt_oi(NMParams p);
TC_API void jlrA_getLong_oi(NMParams p);
TC_API void jlrA_getFloat_oi(NMParams p);
TC_API void jlrA_getDouble_oi(NMParams p);
TC_API void jlrA_set_oio(NMParams p);
TC_API void jlrA_setBoolean_oib(NMParams p);
TC_API void jlrA_setByte_oib(NMParams p);
TC_API void jlrA_setChar_oic(NMParams p);
TC_API void jlrA_setShort_ois(NMParams p);
TC_API void jlrA_setInt_oii(NMParams p);
TC_API void jlrA_setLong_oil(NMParams p);
TC_API void jlrA_setFloat_oid(NMParams p);
TC_API void jlrA_setDouble_oid(NMParams p);
TC_API void jlrM_invoke_oO(NMParams p);
TC_API void jlrC_newInstance_O(NMParams p);
TC_API void jlrF_get_o(NMParams p);
TC_API void jlrF_getBoolean_o(NMParams p);
TC_API void jlrF_getByte_o(NMParams p);
TC_API void jlrF_getChar_o(NMParams p);
TC_API void jlrF_getShort_o(NMParams p);
TC_API void jlrF_getInt_o(NMParams p);
TC_API void jlrF_getLong_o(NMParams p);
TC_API void jlrF_getFloat_o(NMParams p);
TC_API void jlrF_getDouble_o(NMParams p);
TC_API void jlrF_set_oo(NMParams p);
TC_API void jlrF_setBoolean_ob(NMParams p);
TC_API void jlrF_setByte_ob(NMParams p);
TC_API void jlrF_setChar_oc(NMParams p);
TC_API void jlrF_setShort_os(NMParams p);
TC_API void jlrF_setInt_oi(NMParams p);
TC_API void jlrF_setLong_ol(NMParams p);
TC_API void jlrF_setFloat_od(NMParams p);
TC_API void jlrF_setDouble_od(NMParams p);
TC_API void jlR_exec_SSs(NMParams p);
TC_API void jlPI_waitFor(NMParams p);
TC_API void jlPI_exitValue(NMParams p);
TC_API void jlPI_destroy(NMParams p);
TC_API void jncFCI_read(NMParams p);
TC_API void jncFCI_read_Bii(NMParams p);
TC_API void jncFCI_write_Bii(NMParams p);
TC_API void jncFCI_implCloseChannel(NMParams p);
TC_API void tmGM_showAddress_sb(NMParams p);
TC_API void tmGM_showRoute_sssi(NMParams p);
TC_API void tucL_create(NMParams p);
TC_API void tucL_destroy(NMParams p);
TC_API void tuzZL_deflate_ssiib(NMParams p);
TC_API void tuzZL_inflate_ssib(NMParams p);
TC_API void tuzZE_setTime_l(NMParams p);
TC_API void tuzZE_getTime(NMParams p);
TC_API void tuzZF_createZipFile_f(NMParams p);
TC_API void tuzZF_close(NMParams p);
TC_API void tuzZF_entries(NMParams p);
TC_API void tuzZF_getEntry_s(NMParams p);
TC_API void tuzZF_getEntryStream_s(NMParams p);
TC_API void tumS_fromText_s(NMParams p);
TC_API void tumS_toText_s(NMParams p);
TC_API void tumS_play_s(NMParams p);
TC_API void tumS_beep(NMParams p);
TC_API void tumS_tone_ii(NMParams p);
TC_API void tumS_setEnabled_b(NMParams p);
TC_API void tumMC_create(NMParams p);
TC_API void tumMC_nativeStart(NMParams p);
TC_API void tumMC_stop(NMParams p);
TC_API void tumMC_reset(NMParams p);
TC_API void tumMC_nativeClose(NMParams p);
TC_API void tumMC_record_iib(NMParams p);
TC_API void tumC_nativeClick(NMParams p);
TC_API void tumC_nativeFinalize(NMParams p);
TC_API void tumC_getNativeResolutions(NMParams p);
TC_API void tumYP_play_scbii(NMParams p);
TC_API void tuiI_imageLoad_s(NMParams p);
TC_API void tuiI_imageParse_sB(NMParams p);
TC_API void tuiI_changeColors_ii(NMParams p);
TC_API void tuiI_getPixelRow_Bi(NMParams p);
TC_API void tuiI_getModifiedInstance_iiiiiii(NMParams p);
TC_API void tuiI_setCurrentFrame_i(NMParams p);
TC_API void tuiI_applyColor_i(NMParams p);
TC_API void tuiI_nativeEquals_i(NMParams p);
TC_API void tuiI_applyColor2_i(NMParams p);
TC_API void tuiI_setTransparentColor_i(NMParams p);
TC_API void tuiI_applyChanges(NMParams p);
TC_API void tuiI_freeTexture(NMParams p);
TC_API void tuiI_applyFade_i(NMParams p);
TC_API void tuiI_createJpg_si(NMParams p);
TC_API void tuiI_nativeResizeJpeg_ssi(NMParams p);
TC_API void tuiI_getJpegBestFit_sii(NMParams p);
TC_API void tuiI_getJpegScaled_sii(NMParams p);
TC_API void tugG_dither_iiii(NMParams p);
TC_API void tugG_create_g(NMParams p);
TC_API void tugG_drawEllipse_iiii(NMParams p);
TC_API void tugG_fillEllipse_iiii(NMParams p);
TC_API void tugG_drawArc_iiidd(NMParams p);
TC_API void tugG_drawPie_iiidd(NMParams p);
TC_API void tugG_fillPie_iiidd(NMParams p);
TC_API void tugG_drawEllipticalArc_iiiidd(NMParams p);
TC_API void tugG_drawEllipticalPie_iiiidd(NMParams p);
TC_API void tugG_fillEllipticalPie_iiiidd(NMParams p);
TC_API void tugG_drawCircle_iii(NMParams p);
TC_API void tugG_fillCircle_iii(NMParams p);
TC_API void tugG_getPixel_ii(NMParams p);
TC_API void tugG_setPixel_ii(NMParams p);
TC_API void tugG_drawLine_iiii(NMParams p);
TC_API void tugG_drawLine_iiiii(NMParams p);
TC_API void tugG_drawDots_iiii(NMParams p);
TC_API void tugG_drawRect_iiii(NMParams p);
TC_API void tugG_fillRect_iiii(NMParams p);
TC_API void tugG_fillPolygon_IIi(NMParams p);
TC_API void tugG_drawPolygon_IIi(NMParams p);
TC_API void tugG_drawPolyline_IIi(NMParams p);
TC_API void tugG_drawText_sii(NMParams p);
TC_API void tugG_drawText_Ciiii(NMParams p);
TC_API void tugG_drawText_siii(NMParams p);
TC_API void tugG_drawRoundRect_iiiii(NMParams p);
TC_API void tugG_fillRoundRect_iiiii(NMParams p);
TC_API void tugG_copyRect_giiiiii(NMParams p);
TC_API void tugG_drawRoundGradient_iiiiiiiii(NMParams p);
TC_API void tugG_drawImage_iiib(NMParams p);
TC_API void tugG_copyImageRect_iiiiib(NMParams p);
TC_API void tugG_setPixels_IIi(NMParams p);
TC_API void tugG_refresh_iiiiiif(NMParams p);
TC_API void tugG_drawImage_iii(NMParams p);
TC_API void tugG_fillEllipseGradient_iiii(NMParams p);
TC_API void tugG_fillPieGradient_iiidd(NMParams p);
TC_API void tugG_fillEllipticalPieGradient_i(NMParams p);
TC_API void tugG_fillCircleGradient_iii(NMParams p);
TC_API void tugG_fillPolygonGradient_IIi(NMParams p);
TC_API void tugG_getRGB_Iiiiii(NMParams p);
TC_API void tugG_setRGB_Iiiiii(NMParams p);
TC_API void tugG_fadeScreen_i(NMParams p);
TC_API void tugG_drawText_Ciiiibi(NMParams p);
TC_API void tugG_drawText_siiiibi(NMParams p);
TC_API void tugG_drawText_siiiiibi(NMParams p);
TC_API void tugG_drawText_siibi(NMParams p);
TC_API void tugG_drawText_siiibi(NMParams p);
TC_API void tugG_drawThickLine_iiiii(NMParams p);
TC_API void tufF_fontCreate(NMParams p);
TC_API void tufFM_fontMetricsCreate(NMParams p);
TC_API void tufFM_charWidth_c(NMParams p);
TC_API void tufFM_stringWidth_s(NMParams p);
TC_API void tufFM_stringWidth_Cii(NMParams p);
TC_API void tufFM_sbWidth_s(NMParams p);
TC_API void tufFM_sbWidth_sii(NMParams p);
TC_API void tufFM_charWidth_si(NMParams p);
TC_API void tueE_isAvailable(NMParams p);
TC_API void tuC_updateScreen(NMParams p);
TC_API void tuMW_exit_i(NMParams p);
TC_API void tuMW_setTimerInterval_i(NMParams p);
TC_API void tuMW_minimize(NMParams p);
TC_API void tuMW_restore(NMParams p);
TC_API void tuMW_getCommandLine(NMParams p);
TC_API void tuW_pumpEvents(NMParams p);
TC_API void tuW_setSIP_icb(NMParams p);
TC_API void tuW_setDeviceTitle_s(NMParams p);
TC_API void tuW_setOrientation_i(NMParams p);
TC_API void tuW_isSipShown(NMParams p);
TC_API void tsCC_bytes2chars_Bii(NMParams p);
TC_API void tsCC_chars2bytes_Cii(NMParams p);
TC_API void tsUTF8CC_bytes2chars_Bii(NMParams p);
TC_API void tsUTF8CC_chars2bytes_Cii(NMParams p);
TC_API void tsC_equals_BB(NMParams p);
TC_API void tsC_toInt_s(NMParams p);
TC_API void tsC_toString_c(NMParams p);
TC_API void tsC_doubleToIntBits_d(NMParams p);
TC_API void tsC_intBitsToDouble_i(NMParams p);
TC_API void tsC_toString_i(NMParams p);
TC_API void tsC_toString_di(NMParams p);
TC_API void tsC_toDouble_s(NMParams p);
TC_API void tsC_toString_si(NMParams p);
TC_API void tsC_doubleToLongBits_d(NMParams p);
TC_API void tsC_longBitsToDouble_l(NMParams p);
TC_API void tsC_toLowerCase_c(NMParams p);
TC_API void tsC_toUpperCase_c(NMParams p);
TC_API void tsC_unsigned2hex_ii(NMParams p);
TC_API void tsC_hashCode_s(NMParams p);
TC_API void tsC_getBreakPos_fsiib(NMParams p);
TC_API void tsC_insertAt_sic(NMParams p);
TC_API void tsC_append_sci(NMParams p);
TC_API void tsC_toString_l(NMParams p);
TC_API void tsC_toLong_s(NMParams p);
TC_API void tsC_fill_Ciic(NMParams p);
TC_API void tsC_fill_Biib(NMParams p);
TC_API void tsC_fill_Iiii(NMParams p);
TC_API void tsC_fill_Diid(NMParams p);
TC_API void tsC_fill_Siii(NMParams p);
TC_API void tsC_fill_Biii(NMParams p);
TC_API void tsC_fill_Liil(NMParams p);
TC_API void tsC_fill_Oiio(NMParams p);
TC_API void tsC_replace_sss(NMParams p);
TC_API void tsC_getBytes_s(NMParams p);
TC_API void tsC_numberOf_sc(NMParams p);
TC_API void tsC_zeroPad_si(NMParams p);
TC_API void tsC_zeroPad_ii(NMParams p);
TC_API void tsC_dup_ci(NMParams p);
TC_API void tsC_spacePad_sib(NMParams p);
TC_API void tsC_numberPad_si(NMParams p);
TC_API void tsC_numberPad_ii(NMParams p);
TC_API void tsT_update(NMParams p);
TC_API void tsS_refresh(NMParams p);
TC_API void tsV_arrayCopy_oioii(NMParams p);
TC_API void tsV_preallocateArray_oi(NMParams p);
TC_API void tsV_getTimeStamp(NMParams p);
TC_API void tsV_setTime_t(NMParams p);
TC_API void tsV_exitAndReboot(NMParams p);
TC_API void tsV_exec_ssib(NMParams p);
TC_API void tsV_setAutoOff_b(NMParams p);
TC_API void tsV_sleep_i(NMParams p);
TC_API void tsV_getFreeMemory(NMParams p);
TC_API void tsV_gc(NMParams p);
TC_API void tsV_interceptSpecialKeys_I(NMParams p);
TC_API void tsV_debug_s(NMParams p);
TC_API void tsV_alert_s(NMParams p);
TC_API void tsV_clipboardCopy_s(NMParams p);
TC_API void tsV_clipboardPaste(NMParams p);
TC_API void tsV_attachLibrary_s(NMParams p);
TC_API void tsV_privateAttachNativeLibrary_s(NMParams p);
TC_API void tsV_isKeyDown_i(NMParams p);
TC_API void tsV_getFile_s(NMParams p);
TC_API void tsV_getRemainingBattery(NMParams p);
TC_API void tsV_tweak_ib(NMParams p);
TC_API void tsV_getStackTrace_t(NMParams p);
TC_API void tsV_showKeyCodes_b(NMParams p);
TC_API void tsV_turnScreenOn_b(NMParams p);
TC_API void tsV_vibrate_i(NMParams p);
TC_API void tsV_identityHashCode_o(NMParams p);
TC_API void tsR_getInt_iss(NMParams p);
TC_API void tsR_getString_iss(NMParams p);
TC_API void tsR_getBlob_iss(NMParams p);
TC_API void tsR_set_issi(NMParams p);
TC_API void tsR_set_isss(NMParams p);
TC_API void tsR_set_issB(NMParams p);
TC_API void tsR_delete_iss(NMParams p);
TC_API void tsR_list_is(NMParams p);
TC_API void tpcbIPOIC_NewContact(NMParams p);
TC_API void tpcbIPOIC_GetAllContacts(NMParams p);
TC_API void tpcbIPOIC_ViewAllContacts(NMParams p);
TC_API void tpcbIPOIC_getIContactString_s(NMParams p);
TC_API void tpcbIPOIC_removeIContact_s(NMParams p);
TC_API void tpcbIPOIC_editIContact_sssssssss(NMParams p);
TC_API void tpcbIPOIC_newAppointment(NMParams p);
TC_API void tpcbIPOIC_GetAllAppointments(NMParams p);
TC_API void tpcbIPOIC_ViewAllAppointments(NMParams p);
TC_API void tpcbIPOIC_getIAppointmentString_(NMParams p);
TC_API void tpcbIPOIC_removeIAppointment_s(NMParams p);
TC_API void tpcbIPOIC_editIAppointment_sssss(NMParams p);
TC_API void tpcbIPOIC_ViewAllTasks(NMParams p);
TC_API void tpcbIPOIC_GetAllTasks(NMParams p);
TC_API void tpcbIPOIC_newTask(NMParams p);
TC_API void tpcbIPOIC_getITaskString_s(NMParams p);
TC_API void tpcbIPOIC_removeITask_s(NMParams p);
TC_API void tpcbIPOIC_editITask_ssssssssssss(NMParams p);
TC_API void tnsSSL_dispose(NMParams p);
TC_API void tnsSSL_handshakeStatus(NMParams p);
TC_API void tnsSSL_getCipherId(NMParams p);
TC_API void tnsSSL_getSessionId(NMParams p);
TC_API void tnsSSL_getCertificateDN_i(NMParams p);
TC_API void tnsSSL_read_s(NMParams p);
TC_API void tnsSSL_write_Bi(NMParams p);
TC_API void tnsSSL_verifyCertificate(NMParams p);
TC_API void tnsSSL_renegotiate(NMParams p);
TC_API void tnsSSLCTX_create_ii(NMParams p);
TC_API void tnsSSLCTX_dispose(NMParams p);
TC_API void tnsSSLCTX_find_s(NMParams p);
TC_API void tnsSSLCTX_objLoad_iss(NMParams p);
TC_API void tnsSSLCTX_objLoad_iBis(NMParams p);
TC_API void tnsSSLCTX_newClient_sB(NMParams p);
TC_API void tnsSSLCTX_newServer_s(NMParams p);
TC_API void tnsSSLU_getConfig_i(NMParams p);
TC_API void tnsSSLU_displayError_i(NMParams p);
TC_API void tnsSSLU_version(NMParams p);
TC_API void tnCM_loadResources(NMParams p);
TC_API void tnCM_setDefaultConfiguration_is(NMParams p);
TC_API void tnCM_open(NMParams p);
TC_API void tnCM_open_i(NMParams p);
TC_API void tnCM_nativeClose(NMParams p);
TC_API void tnCM_getHostAddress_s(NMParams p);
TC_API void tnCM_getHostName_s(NMParams p);
TC_API void tnCM_getLocalHost(NMParams p);
TC_API void tnCM_getLocalHostName(NMParams p);
TC_API void tnCM_isAvailable_i(NMParams p);
TC_API void tnCM_releaseResources(NMParams p);
TC_API void tnS_socketCreate_siib(NMParams p);
TC_API void tnS_nativeClose(NMParams p);
TC_API void tnS_readWriteBytes_Biib(NMParams p);
TC_API void tnSS_serversocketCreate_iiis(NMParams p);
TC_API void tnSS_nativeClose(NMParams p);
TC_API void tpCI_loadResources(NMParams p);
TC_API void tpCI_releaseResources(NMParams p);
TC_API void tpCI_update(NMParams p);
TC_API void tpD_number_s(NMParams p);
TC_API void tpD_hangup(NMParams p);
TC_API void tpSMS_send_ss(NMParams p);
TC_API void tpSMS_receive(NMParams p);
TC_API void jlO_toStringNative(NMParams p);
TC_API void jlO_nativeHashCode(NMParams p);
TC_API void jlO_getClass(NMParams p);
TC_API void jlO_clone(NMParams p);
TC_API void jlS_toUpperCase(NMParams p);
TC_API void jlS_toLowerCase(NMParams p);
TC_API void jlS_valueOf_d(NMParams p);
TC_API void jlS_valueOf_c(NMParams p);
TC_API void jlS_valueOf_i(NMParams p);
TC_API void jlS_indexOf_i(NMParams p);
TC_API void jlS_indexOf_ii(NMParams p);
TC_API void jlS_equals_o(NMParams p);
TC_API void jlS_compareTo_s(NMParams p);
TC_API void jlS_copyChars_CiCii(NMParams p);
TC_API void jlS_indexOf_si(NMParams p);
TC_API void jlS_indexOf_s(NMParams p);
TC_API void jlS_hashCode(NMParams p);
TC_API void jlS_startsWith_si(NMParams p);
TC_API void jlS_startsWith_s(NMParams p);
TC_API void jlS_endsWith_s(NMParams p);
TC_API void jlS_equalsIgnoreCase_s(NMParams p);
TC_API void jlS_replace_cc(NMParams p);
TC_API void jlS_lastIndexOf_ii(NMParams p);
TC_API void jlS_lastIndexOf_i(NMParams p);
TC_API void jlS_trim(NMParams p);
TC_API void jlS_valueOf_l(NMParams p);
TC_API void jlS_lastIndexOf_s(NMParams p);
TC_API void jlS_lastIndexOf_si(NMParams p);
TC_API void jlS_getBytes(NMParams p);
TC_API void jlSB_ensureCapacity_i(NMParams p);
TC_API void jlSB_setLength_i(NMParams p);
TC_API void jlSB_append_s(NMParams p);
TC_API void jlSB_append_C(NMParams p);
TC_API void jlSB_append_Cii(NMParams p);
TC_API void jlSB_append_c(NMParams p);
TC_API void jlSB_append_i(NMParams p);
TC_API void jlSB_append_l(NMParams p);
TC_API void jlSB_append_d(NMParams p);
TC_API void jlSB_delete_ii(NMParams p);
TC_API void jlT_printStackTraceNative(NMParams p);
TC_API void jlT_yield(NMParams p);
TC_API void jlT_start(NMParams p);
TC_API void jlT_currentThread(NMParams p);
TC_API void juL_getDefaultToString(NMParams p);
TC_API void tidRD_isSupported_i(NMParams p);
TC_API void tidRD_getState_i(NMParams p);
TC_API void tidRD_setState_ii(NMParams p);
TC_API void tidPC_create_iiiii(NMParams p);
TC_API void tidPC_nativeClose(NMParams p);
TC_API void tidPC_setFlowControl_b(NMParams p);
TC_API void tidPC_readCheck(NMParams p);
TC_API void tidPC_readWriteBytes_Biib(NMParams p);
TC_API void tidbDA_nativeDiscoveryAgent(NMParams p);
TC_API void tidbDA_cancelInquiry_d(NMParams p);
TC_API void tidbDA_cancelServiceSearch_i(NMParams p);
TC_API void tidbDA_retrieveDevices_i(NMParams p);
TC_API void tidbDA_nativeSearchServices_IUrd(NMParams p);
TC_API void tidbDA_selectService_uib(NMParams p);
TC_API void tidbDA_startInquiry_id(NMParams p);
TC_API void tidbSPS_createSerialPortServer_s(NMParams p);
TC_API void tidbSPS_accept(NMParams p);
TC_API void tidbSPS_close(NMParams p);
TC_API void tidbSPC_createSerialPortClient_s(NMParams p);
TC_API void tidbSPC_readBytes_Bii(NMParams p);
TC_API void tidbSPC_writeBytes_Bii(NMParams p);
TC_API void tidbSPC_close(NMParams p);
TC_API void tidgGPS_startGPS(NMParams p);
TC_API void tidgGPS_updateLocation(NMParams p);
TC_API void tidgGPS_stopGPS(NMParams p);
TC_API void tiPDBF_create_sssi(NMParams p);
TC_API void tiPDBF_rename_s(NMParams p);
TC_API void tiPDBF_addRecord_i(NMParams p);
TC_API void tiPDBF_addRecord_ii(NMParams p);
TC_API void tiPDBF_resizeRecord_i(NMParams p);
TC_API void tiPDBF_nativeClose(NMParams p);
TC_API void tiPDBF_delete(NMParams p);
TC_API void tiPDBF_listPDBs_ii(NMParams p);
TC_API void tiPDBF_deleteRecord(NMParams p);
TC_API void tiPDBF_getRecordCount(NMParams p);
TC_API void tiPDBF_setRecordPos_i(NMParams p);
TC_API void tiPDBF_readWriteBytes_Biib(NMParams p);
TC_API void tiPDBF_inspectRecord_Bii(NMParams p);
TC_API void tiPDBF_getRecordAttributes_i(NMParams p);
TC_API void tiPDBF_setRecordAttributes_ib(NMParams p);
TC_API void tiPDBF_getAttributes(NMParams p);
TC_API void tiPDBF_setAttributes_i(NMParams p);
TC_API void tiPDBF_searchBytes_Bii(NMParams p);
TC_API void tiF_getDeviceAlias(NMParams p);
TC_API void tiF_create_sii(NMParams p);
TC_API void tiF_close(NMParams p);
TC_API void tiF_isCardInserted_i(NMParams p);
TC_API void tiF_createDir(NMParams p);
TC_API void tiF_delete(NMParams p);
TC_API void tiF_exists(NMParams p);
TC_API void tiF_getSize(NMParams p);
TC_API void tiF_isDir(NMParams p);
TC_API void tiF_listFiles(NMParams p);
TC_API void tiF_readBytes_Bii(NMParams p);
TC_API void tiF_rename_s(NMParams p);
TC_API void tiF_setPos_i(NMParams p);
TC_API void tiF_writeBytes_Bii(NMParams p);
TC_API void tiF_setAttributes_i(NMParams p);
TC_API void tiF_getAttributes(NMParams p);
TC_API void tiF_setTime_bt(NMParams p);
TC_API void tiF_getTime_b(NMParams p);
TC_API void tiF_setSize_i(NMParams p);
TC_API void tiF_getCardSerialNumber_i(NMParams p);
TC_API void tiF_flush(NMParams p);
TC_API void tiF_listRoots(NMParams p);
TC_API void tiF_isEmpty(NMParams p);
TC_API void tiF_chmod_i(NMParams p);
TC_API void tuzCS_createInflate_s(NMParams p);
TC_API void tuzCS_createDeflate_si(NMParams p);
TC_API void tuzCS_readBytes_Bii(NMParams p);
TC_API void tuzCS_writeBytes_Bii(NMParams p);
TC_API void tuzCS_close(NMParams p);
TC_API void tuzZS_createInflate_s(NMParams p);
TC_API void tuzZS_createDeflate_si(NMParams p);
TC_API void tuzZS_available(NMParams p);
TC_API void tuzZS_getNextEntry(NMParams p);
TC_API void tuzZS_putNextEntry_z(NMParams p);
TC_API void tuzZS_closeEntry(NMParams p);
TC_API void tuzZS_readBytes_Bii(NMParams p);
TC_API void tuzZS_writeBytes_Bii(NMParams p);
TC_API void tuzZS_close(NMParams p);
TC_API void tcdMD5D_init(NMParams p);
TC_API void tcdMD5D_process_B(NMParams p);
TC_API void tcdSHA1D_init(NMParams p);
TC_API void tcdSHA1D_process_B(NMParams p);
TC_API void tcdSHA256D_init(NMParams p);
TC_API void tcdSHA256D_process_B(NMParams p);
TC_API void tccAESC_init(NMParams p);
TC_API void tccAESC_doReset(NMParams p);
TC_API void tccAESC_process_B(NMParams p);
TC_API void tccRSAC_init(NMParams p);
TC_API void tccRSAC_finalize(NMParams p);
TC_API void tccRSAC_doReset(NMParams p);
TC_API void tccRSAC_process_B(NMParams p);
TC_API void tcsPKCS1S_init(NMParams p);
TC_API void tcsPKCS1S_finalize(NMParams p);
TC_API void tcsPKCS1S_doReset(NMParams p);
TC_API void tcsPKCS1S_doSign_B(NMParams p);
TC_API void tcsPKCS1S_doVerify_BB(NMParams p);
TC_API void rU_getConfigInfo(NMParams p);
TC_API void rU_getProductInfo(NMParams p);
TC_API void rU_getDeviceInfo(NMParams p);
TC_API void tuBI_add_1_IIii(NMParams p);
TC_API void tuBI_add_n_IIIi(NMParams p);
TC_API void tuBI_sub_n_IIIi(NMParams p);
TC_API void tuBI_mul_1_IIii(NMParams p);
TC_API void tuBI_mul_IIiIi(NMParams p);
TC_API void tuBI_udiv_qrnnd_li(NMParams p);
TC_API void tuBI_divmod_1_IIii(NMParams p);
TC_API void tuBI_submul_1_IiIii(NMParams p);
TC_API void tuBI_divide_IiIi(NMParams p);
TC_API void tuBI_chars_per_word_i(NMParams p);
TC_API void tuBI_count_leading_zeros_i(NMParams p);
TC_API void tuBI_set_str_IBii(NMParams p);
TC_API void tuBI_cmp_IIi(NMParams p);
TC_API void tuBI_cmp_IiIi(NMParams p);
TC_API void tuBI_rshift_IIiii(NMParams p);
TC_API void tuBI_rshift0_IIiii(NMParams p);
TC_API void tuBI_rshift_long_Iii(NMParams p);
TC_API void tuBI_lshift_IiIii(NMParams p);
TC_API void tuBI_findLowestBit_i(NMParams p);
TC_API void tuBI_findLowestBit_I(NMParams p);
TC_API void tuBI_gcd_IIi(NMParams p);
TC_API void tuBI_intLength_i(NMParams p);
TC_API void tuBI_intLength_Ii(NMParams p);
TC_API void tidsS_scannerActivate(NMParams p);
TC_API void tidsS_setBarcodeParam_ib(NMParams p);
TC_API void tidsS_setParam_iii(NMParams p);
TC_API void tidsS_commitBarcodeParams(NMParams p);
TC_API void tidsS_setBarcodeLength_iiii(NMParams p);
TC_API void tidsS_getData(NMParams p);
TC_API void tidsS_getScanManagerVersion(NMParams p);
TC_API void tidsS_getScanPortDriverVersion(NMParams p);
TC_API void tidsS_deactivate(NMParams p);
TC_API void tidsS_readBarcode_s(NMParams p);
TC_API void tidsS_setParam_ss(NMParams p);
TC_API void tdsNDB__open_si(NMParams p);
TC_API void tdsNDB__close(NMParams p);
TC_API void tdsNDB__exec_s(NMParams p);
TC_API void tdsNDB_shared_cache_b(NMParams p);
TC_API void tdsNDB_enable_load_extension_b(NMParams p);
TC_API void tdsNDB_interrupt(NMParams p);
TC_API void tdsNDB_busy_timeout_i(NMParams p);
TC_API void tdsNDB_prepare_s(NMParams p);
TC_API void tdsNDB_errmsg(NMParams p);
TC_API void tdsNDB_libversion(NMParams p);
TC_API void tdsNDB_changes(NMParams p);
TC_API void tdsNDB_total_changes(NMParams p);
TC_API void tdsNDB_finalize_l(NMParams p);
TC_API void tdsNDB_step_l(NMParams p);
TC_API void tdsNDB_reset_l(NMParams p);
TC_API void tdsNDB_clear_bindings_l(NMParams p);
TC_API void tdsNDB_bind_parameter_count_l(NMParams p);
TC_API void tdsNDB_column_count_l(NMParams p);
TC_API void tdsNDB_column_type_li(NMParams p);
TC_API void tdsNDB_column_decltype_li(NMParams p);
TC_API void tdsNDB_column_table_name_li(NMParams p);
TC_API void tdsNDB_column_name_li(NMParams p);
TC_API void tdsNDB_column_text_li(NMParams p);
TC_API void tdsNDB_column_blob_li(NMParams p);
TC_API void tdsNDB_column_double_li(NMParams p);
TC_API void tdsNDB_column_long_li(NMParams p);
TC_API void tdsNDB_column_int_li(NMParams p);
TC_API void tdsNDB_bind_null_li(NMParams p);
TC_API void tdsNDB_bind_int_lii(NMParams p);
TC_API void tdsNDB_bind_long_lil(NMParams p);
TC_API void tdsNDB_bind_double_lid(NMParams p);
TC_API void tdsNDB_bind_text_lis(NMParams p);
TC_API void tdsNDB_bind_blob_liB(NMParams p);
TC_API void tdsNDB_result_null_l(NMParams p);
TC_API void tdsNDB_result_text_ls(NMParams p);
TC_API void tdsNDB_result_blob_lB(NMParams p);
TC_API void tdsNDB_result_double_ld(NMParams p);
TC_API void tdsNDB_result_long_ll(NMParams p);
TC_API void tdsNDB_result_int_li(NMParams p);
TC_API void tdsNDB_backup_ssp(NMParams p);
TC_API void tdsNDB_restore_ssp(NMParams p);
TC_API void tdsNDB_column_metadata_l(NMParams p);
TC_API void tdsNDB_load(NMParams p);
TC_API void tmA_getHeightD_i(NMParams p);
TC_API void tmA_configureD_s(NMParams p);
TC_API void tmA_setSizeD_i(NMParams p);
TC_API void tmA_setPositionD_i(NMParams p);
TC_API void tmA_setVisibleD_b(NMParams p);
TC_API void tmA_isVisibleD(NMParams p);
TC_API void ttTM_nativeInitialize(NMParams p);
TC_API void ttSM_sendTextMessage_sss(NMParams p);
TC_API void ttSM_sendDataMessage_ssiB(NMParams p);
TC_API void ttSM_registerSmsReceiver_si(NMParams p);
TC_API void tfiFII_getToken(NMParams p);
TC_API void tnNM_notify_n(NMParams p);
TC_API void tcpPBKDF2WHSHA1F_generateSecretI(NMParams p);
TC_API void tcspPM_internalPrintText_ssp(NMParams p);
TC_API void tqQRC_nativeGetBytes_sii(NMParams p);
TC_API void tidgGC_open_i(NMParams p);
TC_API void tidgGL_open_gi(NMParams p);
TC_API void tidgGL_requestOutput_si(NMParams p);
TC_API void tidgGL_setValue_i(NMParams p);
TC_API void tidgGL_requestInput_s(NMParams p);
TC_API void tidgGL_getValue(NMParams p);

#ifdef __cplusplus
}
#endif

#endif
