create or replace function fnc_devu_qa_lab_mov (pcod_empresa    in char,
                                                pcod_sucursal   in char,
                                                pcod_planilla   in number,
                                                pcod_movimiento in number)
  return clob is

  vnveces   number;
  vdivision number;
  --
  vcount       number := 1;
  vsql         clob := '';
  vmov_columna clob := '';
  vcolumnas    clob := '';

begin

  select plc_nveces, plc_division
    into vnveces, vdivision
    from qa_planilla_config x
   where x.cod_empresa = pcod_empresa
     and x.cod_sucursal = pcod_sucursal
     and x.cod_planilla = pcod_planilla;

  --si planilla es irregular entonces
  if vdivision = 1 then
    while vcount <= vnveces loop
      dbms_output.put_line(vcount);
      if vcount = 1 then
        vcolumnas    := concat(vcolumnas, 'x."' || vcount || '"');
        vmov_columna := to_char(vcount);
      else
        vcolumnas    := concat(vcolumnas, ', x."' || vcount || '"');
        vmov_columna := vmov_columna || ', ' || to_char(vcount);
      end if;
      vcount := vcount + 1;
    end loop;
  else
    --division = 0 ====> regular
    /* FALTA PROGRAMAR SEGUN CONFIGURACION DE PLANILLA */
    vcolumnas := concat(vcolumnas, ' NULL regular ');
  end if;

  if vnveces > 1 then
    vcolumnas := concat(vcolumnas, ', null Resultado');
  end if;

  vsql := concat(vsql,
                 ' select v.cod_empresa, ' || '        v.cod_sucursal, ' ||
                 '        v.cod_planilla, ' ||
                 '        nvl(c.plc_nveces, 1) plc_nveces, ' ||
                 '        v.cod_variable, ' || '        v.var_formula, ' ||
                 '        decode(v.var_ori_datos,0,' || chr(39) || 'N' ||
                 chr(39) || ',' || chr(39) || 'S' || chr(39) ||
                 ') es_formula, ' || 
                 '        c.plc_tipo, '||
                 '        c.plc_int_div_ini, '||
                 '        c.plc_int_div_fim, '||
                 '        v.var_ordem orden, ' ||
                 '        v.var_nom_var variable, ' ||
                 '        v.var_nom_anali análisis, ' ||
                 '        m.abreviatura medida, ');

  vsql := concat(vsql, vcolumnas);

  vsql := concat(vsql,
                 '  from (select cod_empresa, cod_variable, mov_valor, mov_columna ' ||
                 '          from qa_lab_mov_deta ' ||
                 '         where cod_empresa = ' || chr(39) || pcod_empresa ||
                 chr(39) || '           and cod_movimiento = ' ||
                 pcod_movimiento || ') ' || '         pivot(sum(mov_valor) ' ||
                 '           for mov_columna in( ');

  vsql := concat(vsql, vmov_columna);

  vsql := concat(vsql,
                 ' )) x,  qa_variable v, ' ||
                 '        qa_unidad_medida m, ' ||
                 '        qa_planilla_config c ' ||
                 '  where x.cod_empresa = v.cod_empresa  ' ||
                 '    and x.cod_variable = v.cod_variable ' ||
                 '    and v.cod_empresa = m.cod_empresa ' ||
                 '    and v.cod_unidad = m.cod_unidad ' ||
                 '    and v.cod_empresa = c.cod_empresa ' ||
                 '    and v.cod_sucursal = c.cod_sucursal ' ||
                 '    and v.cod_planilla = c.cod_planilla ' ||
                 '  order by v.var_ordem ');

  return vsql;
end;
