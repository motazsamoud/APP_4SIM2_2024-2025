// update-activite.dto.ts
import { IsString, IsOptional, IsNumber, IsDateString } from 'class-validator';

export class UpdateActiviteDto {
  @IsString()
  @IsOptional()
  title?: string;  // Titre de l'activité
  
  @IsString()
  @IsOptional()
  description?: string;  // Description de l'activité
  
  @IsNumber()
  @IsOptional()
  price?: number;  // Prix de l'activité
  
  @IsDateString()
  @IsOptional()
  startDate?: string;  // Date de début de l'activité
  
  @IsDateString()
  @IsOptional()
  endDate?: string;  // Date de fin de l'activité
  
  @IsString()
  @IsOptional()
  location?: string;  // Lieu de l'activité
  
  @IsOptional()
  image?: string;  // Optionnel : Lien ou chemin vers une image
}
