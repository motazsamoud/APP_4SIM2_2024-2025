// create-activite.dto.ts
import { IsString, IsNotEmpty, IsOptional, IsNumber, IsDateString } from 'class-validator';

export class CreateActiviteDto {
  @IsString()
  @IsNotEmpty()
  title: string;  // Titre de l'activité
  
  @IsString()
  @IsOptional()
  description?: string;  // Description de l'activité
  
  @IsNumber()
  @IsNotEmpty()
  price: number;  // Prix de l'activité
  
  @IsDateString()
  @IsNotEmpty()
  startDate: string;  // Date de début de l'activité
  
  @IsDateString()
  @IsNotEmpty()
  endDate: string;  // Date de fin de l'activité
  
  @IsString()
  @IsNotEmpty()
  location: string;  // Lieu de l'activité
  
  @IsOptional()
  image?: string;  // Optionnel : Lien ou chemin vers une image
}

